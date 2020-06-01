/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import java.util.regex.Pattern;

import static loa.Piece.*;
import static loa.Square.*;

/** Represents the state of a game of Lines of Action.
 *  @author Xiuhui Ming
 */
class Board {

    /** Default number of moves for each side that results in a draw. */
    static final int DEFAULT_MOVE_LIMIT = 60;

    /** Pattern describing a valid square designator (cr). */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /** A Board whose initial contents are taken from INITIALCONTENTS
     *  and in which the player playing TURN is to move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row][col]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     *
     *  CAUTION: The natural written notation for arrays initializers puts
     *  the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /** A new board in the standard initial position. */
    Board() {
        this(INITIAL_PIECES, BP);
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move. */
    void initialize(Piece[][] contents, Piece side) {
        for (int c = 0; c < contents.length; c += 1) {
            for (int r = 0; r < contents.length; r += 1) {
                Piece p = contents[r][c];
                Square sq = sq(c, r);
                set(sq, p);
            }
        }
        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;
        _winner = null;
    }

    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /** Set my state to a copy of BOARD. */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }
        for (int c = 0; c < 8; c += 1) {
            for (int r = 0; r < 8; r += 1) {
                Piece p = board._board[sq(c, r).index()];
                Square s = sq(c, r);
                set(s, p);
            }
        }
        _moveLimit = board._moveLimit;
        _turn = board._turn;
        _moves.clear();
        for (Move m : board._moves) {
            _moves.add(m);
        }
    }

    /** Return the contents of the square at SQ. */
    Piece get(Square sq) {
        return _board[sq.index()];
    }

    /** Set the square at SQ to V and set the side that is to move next
     *  to NEXT, if NEXT is not null. */
    void set(Square sq, Piece v, Piece next) {
        _board[sq.index()] = v;
        if (next != null) {
            _turn = next;
        }
    }

    /** Set the square at SQ to V, without modifying the side that
     *  moves next. */
    void set(Square sq, Piece v) {
        set(sq, v, null);
    }

    /** Set limit on number of moves by each side that results in a tie to
     *  LIMIT, where 2 * LIMIT > movesMade(). */
    void setMoveLimit(int limit) {
        if (2 * limit <= movesMade()) {
            throw new IllegalArgumentException("move limit too small");
        }
        _moveLimit = 2 * limit;
    }

    /** Assuming isLegal(MOVE), make MOVE. This function assumes that
     *  MOVE.isCapture() will return false.  If it saves the move for
     *  later retraction, makeMove itself uses MOVE.captureMove() to produce
     *  the capturing move. */
    void makeMove(Move move) {
        assert isLegal(move);
        if (_board[move.getTo().index()]
                == _board[move.getFrom().index()].opposite()) {
            move = move.captureMove();
        }
        _moves.add(move);
        Piece p = _board[move.getFrom().index()];
        set(move.getFrom(), EMP);
        set(move.getTo(), p, p.opposite());
        _subsetsInitialized = false;
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0.  */
    void retract() {
        assert movesMade() > 0;
        Move m = _moves.get(movesMade() - 1);
        Piece p = _board[(m.getTo()).index()];
        _board[m.getFrom().index()] = p;
        if (m.isCapture()) {
            _board[m.getTo().index()] = p.opposite();
        } else {
            _board[m.getTo().index()] = EMP;
        }
        _moves.remove(movesMade() - 1);
        _turn = _turn.opposite();
        _subsetsInitialized = false;
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff FROM - TO is a legal move for the player currently on
     *  move. */
    boolean isLegal(Square from, Square to) {
        if (!exists(from.col(), from.row()) || !exists(to.col(), to.row())) {
            return false;
        }
        if (!from.isValidMove(to)) {
            return false;
        }
        if (_board[from.index()] != turn()) {
            return false;
        }
        int numSpaces = from.distance(to);
        int dir = from.direction(to);
        int numPieces = numPieces(from, dir);
        if (numPieces != numSpaces) {
            return false;
        }
        return blocked(from, to);
    }

    /** Return true iff MOVE is legal for the player currently on move.
     *  The isCapture() property is ignored. */
    boolean isLegal(Move move) {
        return isLegal(move.getFrom(), move.getTo());
    }

    /** Return a sequence of all legal moves from this position. */
    void findLegalMoves() {
        for (int c = 0; c < Math.sqrt(_board.length); c += 1) {
            for (int r = 0; r < Math.sqrt(_board.length); r += 1) {
                Square sq = sq(c, r);
                if (_board[sq.index()] == _turn) {
                    for (int i = 0; i < 8; i += 1) {
                        int numPieces = numPieces(sq, i);
                        Square sqTo = sq.moveDest(i, numPieces);
                        if (sqTo != null && isLegal(sq, sqTo)) {
                            Move m = Move.mv(sq, sqTo);
                            legalMoves.add(m);
                        }
                    }
                }
            }
        }
    }

    /** Get list of legal moves.
     * @return legalMoves */
    ArrayList<Move> getLegalMoves() {
        legalMoves.clear();
        findLegalMoves();
        return legalMoves;
    }

    /** Return true iff the game is over (either player has all his
     *  pieces continguous or there is a tie). */
    boolean gameOver() {
        return winner() != null;
    }

    /** Returns the number of pieces along a line of action
     * given a square and a direction.
     * @param sq jesus
     * @param dir christ */
    int numPieces(Square sq, int dir) {
        int num = 0;
        Square s = sq;
        while (s != null) {
            if (_board[s.index()] != EMP) {
                num += 1;
            }
            s = s.moveDest(dir, 1);
        }
        dir = (dir + 4) % 8;
        s = sq.moveDest(dir, 1);
        while (s != null) {
            if (_board[s.index()] != EMP) {
                num += 1;
            }
            s = s.moveDest(dir, 1);
        }
        return num;
    }

    /** Return true iff SIDE's pieces are continguous. */
    boolean piecesContiguous(Piece side) {
        return getRegionSizes(side).size() == 1;
    }

    /** Return the winning side, if any.  If the game is not over, result is
     *  null.  If the game has ended in a tie, returns EMP. */
    Piece winner() {
        if (!_winnerKnown) {
            _winner = null;
            if (piecesContiguous(BP) && piecesContiguous(WP)) {
                _winnerKnown = true;
                _winner = _turn;
            } else if (piecesContiguous(BP)) {
                _winnerKnown = true;
                _winner = BP;
            } else if (piecesContiguous(WP)) {
                _winnerKnown = true;
                _winner = WP;
            } else if (movesMade() / 2 >= _moveLimit) {
                _winnerKnown = true;
                _winner = EMP;
            }
        }
        return _winner;
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return Arrays.deepEquals(_board, b._board) && _turn == b._turn;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_board) * 2 + _turn.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = BOARD_SIZE - 1; r >= 0; r -= 1) {
            out.format("    ");
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                out.format("%s ", get(sq(c, r)).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /** Return true if a move from FROM to TO is blocked by an opposing
     *  piece or by a friendly piece on the target square. */
    private boolean blocked(Square from, Square to) {
        int num = from.distance(to);
        for (int i = 1; i < num; i += 1) {
            if (_board[from.moveDest(from.direction(to), i).index()]
                    == _board[from.index()].opposite()) {
                return false;
            }
        }
        if (_board[from.index()] == _board[to.index()]) {
            return false;
        }
        return true;
    }

    /** Return the size of the as-yet unvisited cluster of squares
     *  containing P at and adjacent to SQ.  VISITED indicates squares that
     *  have already been processed or are in different clusters.  Update
     *  VISITED to reflect squares counted. */
    private int numContig(Square sq, boolean[][] visited, Piece p) {
        if (p == EMP) {
            return 0;
        }
        if (_board[sq.index()] != p) {
            return 0;
        }
        if (visited[sq.col()][sq.row()]) {
            return 0;
        }
        int sum = 1;
        visited[sq.col()][sq.row()] = true;
        cluster.add(sq);
        for (Square s : sq.adjacent()) {
            sum += numContig(s, visited, p);
        }
        if (p.fullName().equals("black")) {
            setBlackVisited(visited);
        } else {
            setWhiteVisited(visited);
        }
        return sum;
    }

    /** Set the values of _whiteRegionSizes and _blackRegionSizes. */
    private void computeRegions() {
        if (_subsetsInitialized) {
            return;
        }
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();
        blackCenter.clear();
        whiteCenter.clear();
        for (int c = 0; c < Math.sqrt(_board.length); c += 1) {
            for (int r = 0; r < Math.sqrt(_board.length); r += 1) {
                whiteVisited[c][r] = false;
                blackVisited[c][r] = false;
            }
        }
        for (int c = 0; c < Math.sqrt(_board.length); c += 1) {
            for (int r = 0; r < Math.sqrt(_board.length); r += 1) {
                cluster.clear();
                if (_board[sq(c, r).index()].fullName().equals("black")) {
                    if (!blackVisited[c][r]) {
                        _blackRegionSizes.add(numContig(sq(c, r),
                                blackVisited, _board[sq(c, r).index()]));
                        blackCenter.add(center(cluster));
                    }
                }
                if (_board[sq(c, r).index()].fullName().equals("white")) {
                    if (!whiteVisited[c][r]) {
                        _whiteRegionSizes.add(numContig(sq(c, r),
                                whiteVisited, _board[sq(c, r).index()]));
                        whiteCenter.add(center(cluster));
                    }
                }
            }
        }
        Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
        Collections.sort(_blackRegionSizes, Collections.reverseOrder());
        _subsetsInitialized = true;
    }

    /** Get center of cluster.
     * @param cluster1 yes
     * @return center*/
    private int center(ArrayList<Square> cluster1) {
        int x = 0;
        int y = 0;
        for (Square sq : cluster1) {
            x += sq.col();
            y += sq.row();
        }
        x = x / cluster1.size();
        y = y / cluster1.size();
        return x * 10 + y;
    }

    /** Get black avg center distance.
     * @return dist */
    int getBlackDistance() {
        int dist = 0;
        if (blackCenter.size() == 1) {
            return 0;
        }
        for (int i = 0; i < blackCenter.size() - 1; i += 1) {
            for (int j = i + 1; j < blackCenter.size(); j += 1) {
                dist += distance(blackCenter.get(i), blackCenter.get(j));
            }
        }
        return dist / blackCenter.size();
    }

    /** Get white avg center distance.
     * @return dist */
    int getWhiteDistance() {
        int dist = 0;
        if (whiteCenter.size() == 1) {
            return 0;
        }
        for (int i = 0; i < whiteCenter.size() - 1; i += 1) {
            for (int j = i + 1; j < whiteCenter.size(); j += 1) {
                dist += distance(whiteCenter.get(i), whiteCenter.get(j));
            }
        }
        return dist / whiteCenter.size();
    }

    /** Distance calculator.
     * @param x x
     * @param y y
     * @return dist */
    private int distance(Integer x, Integer y) {
        int xx = x / 10;
        int yx = y / 10;
        int xy = x % 10;
        int yy = y % 10;
        return (int) Math.sqrt(Math.pow((xx - yx), 2) + Math.pow((xy - yy), 2));
    }
    /** Helper function for computeRegions.
     * @param visited visit */
    private void setBlackVisited(boolean[][] visited) {
        blackVisited = visited;
    }

    /** Helper function for computeRegions.
     * @param visited visit */
    private void setWhiteVisited(boolean[][] visited) {
        whiteVisited = visited;
    }

    /** Return the sizes of all the regions in the current union-find
     *  structure for side S. */
    List<Integer> getRegionSizes(Piece s) {
        computeRegions();
        if (s == WP) {
            return _whiteRegionSizes;
        } else {
            return _blackRegionSizes;
        }
    }

    /** Cluster. */
    private ArrayList<Square> cluster = new ArrayList<>();

    /** Centers. */
    private ArrayList<Integer>
            blackCenter = new ArrayList<>(),
            whiteCenter = new ArrayList<>();

    /** Legal moves. */
    private ArrayList<Move> legalMoves = new ArrayList<>();


    /** The standard initial configuration for Lines of Action (bottom row
     *  first). */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** Current contents of the board.  Square S is at _board[S.index()]. */
    private final Piece[] _board = new Piece[BOARD_SIZE  * BOARD_SIZE];

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;
    /** Limit on number of moves before tie is declared.  */
    private int _moveLimit;
    /** True iff the value of _winner is known to be valid. */
    private boolean _winnerKnown;
    /** Cached value of the winner (BP, WP, EMP (for tie), or null (game still
     *  in progress).  Use only if _winnerKnown. */
    private Piece _winner;

    /** True iff subsets computation is up-to-date. */
    private boolean _subsetsInitialized;

    /** List of the sizes of continguous clusters of pieces, by color. */
    private final ArrayList<Integer>
        _whiteRegionSizes = new ArrayList<>(),
        _blackRegionSizes = new ArrayList<>();


    /** Visited. */
    private boolean[][]
        blackVisited = new boolean[(int) Math.sqrt(_board.length)]
            [(int) Math.sqrt(_board.length)],
        whiteVisited = new boolean[(int) Math.sqrt(_board.length)]
                [(int) Math.sqrt(_board.length)];
}
