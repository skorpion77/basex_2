package org.basex.query.func;

import static org.basex.query.QueryError.*;
import static org.basex.query.func.Function.*;

import org.basex.query.*;
import org.junit.*;

/**
 * This class tests the functions of the Fetch Module.
 *
 * @author BaseX Team 2005-18, BSD License
 * @author Christian Gruen
 */
public final class FetchModuleTest extends AdvancedQueryTest {
  /** Test file. */
  private static final String XML = "src/test/resources/input.xml";
  /** Test file. */
  private static final String CSV = "src/test/resources/input.csv";

  /** Test method. */
  @Test public void text() {
    final Function func = _FETCH_TEXT;
    // successful queries
    query(func.args(XML));
    error(func.args(XML + 'x'), FETCH_OPEN_X);
    error(func.args(XML, "xxx"), FETCH_ENCODING_X);
  }

  /** Test method. */
  @Test public void xml() {
    final Function func = _FETCH_XML;
    // successful queries
    query(func.args(XML));
    query("exists(" + func.args(XML, " map { 'chop':true() }") +
        "//text()[not(normalize-space())])", false);
    query("exists(" + func.args(XML, " map { 'chop':false() }") +
        "//text()[not(normalize-space())])", true);
    query(COUNT.args(func.args(CSV,
        " map { 'parser':'csv','csvparser': 'header=true' }") + "//City"), 3);
    query(COUNT.args(func.args(CSV,
        " map { 'parser':'csv','csvparser': map { 'header': true() } }") + "//City"), 3);
    query(COUNT.args(func.args(CSV,
        " map { 'parser':'csv','csvparser': map { 'header': 'true' } }") + "//City"), 3);
    error(func.args(XML, " map { 'parser': 'unknown' }"), BASEX_OPTIONS_X_X);
    error(func.args(XML + 'x'), FETCH_OPEN_X);
  }

  /** Test method. */
  @Test public void xmlBinary() {
    final Function func = _FETCH_XML_BINARY;
    // successful queries
    query(func.args(_CONVERT_STRING_TO_BASE64.args("<x/>")), "<x/>");
    final String encoding = "CP1252";
    final String xml = "<x>Ä</x>";
    final String data = "<?xml version=''1.0'' encoding=''" + encoding + "''?>" + xml;
    query(func.args(_CONVERT_STRING_TO_BASE64.args(" '" + data + '\'', encoding)), xml);
  }

  /** Test method. */
  @Test public void binary() {
    final Function func = _FETCH_BINARY;
    // successful queries
    query(func.args(XML));
    error(func.args(XML + 'x'), FETCH_OPEN_X);
  }

  /** Test method. */
  @Test public void contentType() {
    final Function func = _FETCH_CONTENT_TYPE;
    // successful queries
    query(func.args(XML));
    error(func.args(XML + 'x'), FETCH_OPEN_X);
  }
}
