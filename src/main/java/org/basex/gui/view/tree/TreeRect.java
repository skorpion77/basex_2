package org.basex.gui.view.tree;


/**
 * This is class is used to handle rectangles.
 * @author Workgroup DBIS, University of Konstanz 2005-10, ISC License
 * @author Wolfgang Miller
 */
final class TreeRect {
  /** X position. */
  int x;
  /** Width. */
  int w;

  /**
   * Initializes TreeRect.
   * @param xx x position
   * @param ww width
   */
  TreeRect(final int xx, final int ww) {
    x = xx;
    w = ww;
  }

  /**
   * Verifies if the specified coordinates are inside the rectangle.
   * @param xx x position
   * @return result of comparison
   */
  boolean contains(final int xx) {
    return xx >= x && xx <= x + w;
  }
  /**  
   * Verifies if the specified coordinates are inside the rectangle.
   * @param xx x position
   * @param ww width
   * @return result of comparison
   */
  boolean contains(final int xx, final int ww) {
    return xx <= x && xx + ww >= x || xx <= x + w && xx + ww >= x;
  }
}