package org.basex.gui;

import static org.basex.core.Text.*;
import static org.basex.gui.GUICommands.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.UIManager;
import org.basex.core.Prop;
import org.basex.core.Text;
import org.basex.gui.layout.BaseXLayout;
import org.basex.gui.view.View;
import org.basex.io.IO;

/**
 * GUI Constants used in different views.
 *
 * To add a new view, please proceed as follows:<br/>
 * <br/>
 * All views have unique names, which are defined below.
 * The following steps are necessary to add a new view
 * (the implementation of the existing views might help you):
 *
 * <ul>
 *  <li> define a unique name for your view (e.g. <code>map</code>)</li>
 *  <li> add a string for your view, as shown below</li>
 *  <li> add the string in the {@link #LAYOUT} string below</li>
 *  <li> create your view implementation in a new sub package
 *    (e.g. {@link org.basex.gui.view.map.MapView}).
 *  <li> add a new {@link View} instance in the {@link GUI} constructor.</li>
 * </ul>
 *
 * Add some more code to allow switching on/off your view:
 *
 * <ul>
 *  <li> add a boolean visibility flag with the view name included
 *    in the {@link GUIProp} class {@link GUIProp#SHOWMAP})</li>
 *  <li> add strings for the menu text and command description in the
 *    {@link Text} class (e.g. {@link Text#GUISHOWMAP} an
 *    {@link Text#GUISHOWMAPTT}).
 *  <li> optionally add localized translations in the .lang files
 *    (e.g. <code>c_showmap</code> and <code>c_showmaptt</code>)
 *  <li> add a corresponding command in the {@link GUICommands} class
 *   (e.g. {@link GUICommands#SHOWMAP})and add a reference in the
 *   {@link #MENUITEMS} menu structure</li>
 * </ul>
 *
 * @author Workgroup DBIS, University of Konstanz 2005-10, ISC License
 * @author Christian Gruen
 */
public final class GUIConstants {
  // VIEW NAMES ===============================================================

  /** Internal name of the Map View. */
  public static final String MAPVIEW = "map";
  /** Internal name of the Tree View. */
  public static final String FOLDERVIEW = "folder";
  /** Internal name of the Text View. */
  public static final String TEXTVIEW = "text";
  /** Internal name of the Table View. */
  public static final String TABLEVIEW = "table";
  /** Internal name of the Info View. */
  public static final String INFOVIEW = "info";
  /** Internal name of the Explore View. */
  public static final String EXPLOREVIEW = "explore";
  /** Internal name of the Plot View. */
  public static final String PLOTVIEW = "plot";
  /** Internal name of the Tree View. */
  public static final String TREEVIEW = "tree";
  /** Internal name of the XQuery View. */
  public static final String XQUERYVIEW = "xquery";

  /**
   * Default GUI Layout. The layout is formatted as follows:
   * The character 'H' or 'V' adds a new horizontal or vertical level,
   * and a level is closed again with the '-' character. All views are
   * separated with spaces, and all views must be specified in this layout.
   * This layout is displayed as soon as a database is opened.
   */
  public static final String LAYOUT = "H V " + XQUERYVIEW + " " +
    TEXTVIEW + " " + EXPLOREVIEW + " - V " + INFOVIEW + " " + MAPVIEW + " " +
    TABLEVIEW + " " + PLOTVIEW + " " + FOLDERVIEW +  " " + TREEVIEW + " - -";

  // TOOLBAR ==================================================================

  /** Toolbar entries, containing the button commands. */
  static final GUICommand[] TOOLBAR = {
    GOBACK, GOUP, GOFORWARD, GOHOME, null, CREATE, OPEN, INFO, null,
      SHOWXQUERY, SHOWINFO, null, SHOWTEXT, SHOWMAP, SHOWFOLDER,
      SHOWTABLE, SHOWPLOT, SHOWEXPLORE, null, SHOWHELP
  };

  // MENUBARS =================================================================

  /** Top menu entries. */
  static final String[] MENUBAR = {
      Text.MENUFILE, Text.MENUEDIT, Text.MENUVIEW, Text.MENUOPTIONS,
      Text.MENUDEEPFS, Text.MENUHELP };

  /** Separator. */
  static final String SEPARATOR = "-";

  /** Two-dimensional Menu entries, containing the menu item commands. */
  static final Object[][] MENUITEMS = { {
    MENUDB, CREATE, OPEN, INFO, EXPORT, DROP, CLOSE, SEPARATOR,
    XQOPEN, XQSAVE, XQSAVEAS, SEPARATOR,
    SERVER, Prop.MAC ? null : SEPARATOR,
    Prop.MAC ? null : EXIT
  }, {
    COPY, PASTE, DELETE, INSERT, EDIT, SEPARATOR,
    SHOWXQUERY, SHOWINFO, SEPARATOR,
    COPYPATH, FILTER
  }, {
    MENUMAIN, SHOWMENU, SHOWBUTTONS, SHOWINPUT, SHOWSTATUS, SEPARATOR,
    MENUVIEWS, SHOWTEXT, SHOWMAP, SHOWFOLDER, SHOWTABLE, SHOWPLOT,
    SHOWEXPLORE, SEPARATOR, FULL
  }, {
    MENUINTER, RTEXEC, RTFILTER, SEPARATOR,
    MENULAYOUT, COLOR, FONTS, MAPLAYOUT,
    Prop.MAC ? null : SEPARATOR, Prop.MAC ? null : PREFS
  }, {
    CREATEFS, SEPARATOR, DQE, MOUNTFS
  }, {
    SHOWHELP, SHOWCOMMUNITY, Prop.MAC ? null : SEPARATOR,
    Prop.MAC ? null : ABOUT
  }};

  /** Context menu entries. */
  public static final GUICommand[] POPUP = {
    GOBACK, FILTER, null, COPY, PASTE, DELETE, INSERT, EDIT, null, COPYPATH
  };

  // CURSORS ==================================================================

  /** Arrow cursor. */
  public static final Cursor CURSORARROW = new Cursor(Cursor.DEFAULT_CURSOR);
  /** Hand cursor. */
  public static final Cursor CURSORHAND = new Cursor(Cursor.HAND_CURSOR);
  /** Wait cursor. */
  public static final Cursor CURSORWAIT = new Cursor(Cursor.WAIT_CURSOR);
  /** Left/Right arrow cursor. */
  public static final Cursor CURSORMOVEH = new Cursor(Cursor.E_RESIZE_CURSOR);
  /** Move cursor. */
  public static final Cursor CURSORMOVEV = new Cursor(Cursor.N_RESIZE_CURSOR);
  /** Text cursor. */
  public static final Cursor CURSORTEXT = new Cursor(Cursor.TEXT_CURSOR);
  /** Move cursor. */
  public static final Cursor CURSORMOVE = new Cursor(Cursor.MOVE_CURSOR);

  /** Icon type. */
  public enum Msg {
    /** Warning icon. */
    WARN("warn", "warning"),
    /** Error icon. */
    ERR("error", "error"),
    /** Success icon. */
    OK("ok", "information");

    /** Small icon. */
    public final Icon small;
    /** Large icon. */
    public final Icon large;

    /**
     * Constructor.
     * @param s small icon
     * @param l large icon
     */
    private Msg(final String s, final String l) {
      small = BaseXLayout.icon(s);
      large = UIManager.getIcon("OptionPane." + l + "Icon");
    }
  };

  /** Background fill options. */
  public static enum Fill {
    /** Opaque fill mode.  */ PLAIN,
    /** Transparent mode.  */ NONE,
    /** Upward gradient.   */ UP,
    /** Downward gradient. */ DOWN
  };

  // COLORS ===================================================================

  /** Error color. */
  public static final Color COLORERROR = new Color(208, 0, 0);
  /** Error highlight color. */
  public static final Color COLORERRHIGH = new Color(255, 200, 180);

  /** Cell color. */
  public static final Color COLORCELL = new Color(224, 224, 224);
  /** Button color. */
  public static final Color COLORBUTTON = new Color(160, 160, 160);
  /** Background color. */
  public static final Color COLORDARK = new Color(64, 64, 64);

  /** Colors of full-text hits. */
  public static final Color COLORFT = new Color(0, 192, 0);

  /** Transparent background color. */
  public static Color back;
  /** Transparent frame color. */
  public static Color frame;

  /** GUI color. */
  public static Color color;
  /** Bright GUI color. */
  public static Color color1;
  /** Second bright GUI color. */
  public static Color color2;
  /** Middle color. */
  public static Color color3;
  /** Middle color. */
  public static Color color4;
  /** Dark color. */
  public static Color color6;

  /** Mark color, custom alpha value. */
  public static Color colormarkA;
  /** Second mark color, custom alpha value. */
  public static Color colormark2A;
  /** Mark color. */
  public static Color colormark1;
  /** Second mark color. */
  public static Color colormark2;
  /** Third mark color. */
  public static Color colormark3;
  /** Fourth mark color. */
  public static Color colormark4;

  /** Cached treemap colors. */
  public static final Color[] COLORS = new Color[IO.MAXHEIGHT];

  // FONTS ====================================================================

  /** Default monospace font. */
  public static Font dfont;
  /** Large font. */
  public static Font lfont;
  /** Font. */
  public static Font font;
  /** Monospace font. */
  public static Font mfont;
  /** Monospace character widths. */
  public static int[] mfwidth;
  /** Bold Font. */
  public static Font bfont;

  /** Default monospace font widths. */
  private static int[] dwidth;
  /** Character large character widths. */
  private static int[] lwidth;
  /** Character widths. */
  private static int[] fwidth;
  /** Bold character widths. */
  private static int[] bwidth;

  /** Private constructor, preventing class instantiation. */
  private GUIConstants() { }

  /**
   * Initializes colors.
   * @param prop gui properties
   */
  public static void init(final GUIProp prop) {
    final int r = prop.num(GUIProp.COLORRED);
    final int g = prop.num(GUIProp.COLORGREEN);
    final int b = prop.num(GUIProp.COLORBLUE);

    // calculate color c:
    // c = (255 - expectedColor) * 10 / factor (= GUIRED/BLUE/GREEN)
    color = new Color(col(r, 110), col(g, 150), col(b, 160), 100);
    color1 = new Color(col(r, 8), col(g, 7), col(b, 6));
    color2 = new Color(col(r, 24), col(g, 25), col(b, 40));
    color3 = new Color(col(r, 32), col(g, 32), col(b, 44));
    color4 = new Color(col(r, 48), col(g, 50), col(b, 40));
    color6 = new Color(col(r, 140), col(g, 100), col(b, 70));

    colormarkA = new Color(col(r, 32), col(g, 160), col(b, 320), 100);
    colormark2A = new Color(col(r, 16), col(g, 80), col(b, 160), 100);
    colormark1 = new Color(col(r, 16), col(g, 120), col(b, 240));
    colormark2 = new Color(col(r, 16), col(g, 80), col(b, 160));
    colormark3 = new Color(col(r, 32), col(g, 160), col(b, 320));
    colormark4 = new Color(col(r, 1), col(g, 40), col(b, 80));

    // create color array
    for(int l = 1; l < 257; l++) {
      COLORS[l - 1] = new Color(Math.max(255 - l * r, 0),
        Math.max(255 - l * g, 0), Math.max(255 - l * b, 0));
    }
    final Color c = COLORS[16];
    back = new Color(c.getRed(), c.getGreen(), c.getBlue(), 40);
    frame = new Color(c.getRed(), c.getGreen(), c.getBlue(), 100);

    dfont = new Font(prop.get(GUIProp.MONOFONT), 0,
        UIManager.getFont("TextArea.font").getSize() - 1);
    dwidth = new Container().getFontMetrics(dfont).getWidths();

    initFonts(prop);
  }

  /**
   * Converts color value with specified factor.
   * @param c color
   * @param f factor
   * @return converted color value
   */
  private static int col(final int c, final int f) {
    return Math.max(0, 255 - c * f / 10);
  }

  /**
   * Initializes fonts.
   * @param prop gui properties
   */
  public static void initFonts(final GUIProp prop) {
    final Container comp = new Container();
    final String f = prop.get(GUIProp.FONT);
    final int type = prop.num(GUIProp.FONTTYPE);
    final int size = prop.num(GUIProp.FONTSIZE);
    font  = new Font(f, type, size);
    mfont = new Font(prop.get(GUIProp.MONOFONT), type, size);
    bfont = new Font(f, Font.BOLD, size);
    lfont = new Font(f, type, size + 8);
    fwidth  = comp.getFontMetrics(font).getWidths();
    lwidth  = comp.getFontMetrics(lfont).getWidths();
    mfwidth = comp.getFontMetrics(mfont).getWidths();
    bwidth  = comp.getFontMetrics(bfont).getWidths();
  }

  /**
   * Returns the character widths for the current font.
   * @param f font reference
   * @return character widths
   */
  public static int[] fontWidths(final Font f) {
    if(f == font) return fwidth;
    if(f == mfont) return mfwidth;
    if(f == bfont) return bwidth;
    if(f == lfont) return lwidth;
    if(f == dfont) return dwidth;
    return new Container().getFontMetrics(f).getWidths();
  }
}
