package org.basex.util;

import org.basex.util.hash.*;

/**
 * <p>This class provides mapping tables for converting full-text tokens.</p>
 *
 * @author BaseX Team 2005-17, BSD License
 * @author Christian Gruen
 */
public final class FTToken {
  /** Hidden constructor. */
  private FTToken() { }

  /**
   * Returns true if the specified character is whitespace.
   * @param ch character to be tested
   * @return result of check
   */
  public static boolean ws(final int ch) {
    return Character.isWhitespace(ch);
  }

  /**
   * Returns true if the specified character is a valid full-text letter or digit.
   * @param ch character to be tested
   * @return result of check
   */
  public static boolean lod(final int ch) {
    final int t = Character.getType(ch);
    return isLOD(t) || isCombining(t);
  }

  /**
   * Returns a token without diacritics.
   * @param token token to be normalized
   * @return resulting token
   */
  public static byte[] noDiacritics(final byte[] token) {
    final int tl = token.length;
    final TokenBuilder tb = new TokenBuilder(tl);
    for(int c = 0; c < tl; c += Token.cl(token, c)) {
      int cp = Token.cp(token, c);
      if(cp >= 0x80) {
        if(isCombining(Character.getType(cp))) continue;
        cp = noDiacritics(cp);
      }
      tb.add(cp);
    }
    return tb.finish();
  }

  /**
   * Checks if a character is a letter or digit.
   * @param type character type
   * @return result of check
   */
  public static boolean isLOD(final int type) {
    return ((1 << Character.UPPERCASE_LETTER |
      1 << Character.LOWERCASE_LETTER |
      1 << Character.TITLECASE_LETTER |
      1 << Character.MODIFIER_LETTER |
      1 << Character.OTHER_LETTER |
      1 << Character.DECIMAL_DIGIT_NUMBER) >> type & 1)
      != 0;
  }

  /**
   * Checks if a character is a combining mark.
   * @param type character type
   * @return result of check
   */
  private static boolean isCombining(final int type) {
    return (1 << type & (
      1 << Character.NON_SPACING_MARK |
      1 << Character.COMBINING_SPACING_MARK |
      1 << Character.ENCLOSING_MARK
    )) != 0;
  }

  /**
   * Returns a codepoint without diacritics.
   * @param cp codepoint to be normalized
   * @return resulting character
   */
  public static int noDiacritics(final int cp) {
    if(cp < 0xC0) return cp;
    if(cp < 0x500) return NORM_0000[cp - 0xC0];
    if(cp > 0x1e00 && cp < 0x2000) return NORM_1E00[cp - 0x1E00];
    final int c = NORM_XXXX.get(cp);
    return c == Integer.MIN_VALUE ? cp : c;
  }

  /** Codepoint mappings (basic). */
  private static final char[][] NC0000 = {

    // Western characters
    { '\u00C0', 'A'}, { '\u00C1', 'A'}, { '\u00C2', 'A'}, { '\u00C3', 'A'}, { '\u00C4', 'A'},
    { '\u00C5', 'A'}, { '\u00C6', 'A'}, { '\u00C7', 'C'}, { '\u00C8', 'E'}, { '\u00C9', 'E'},
    { '\u00CA', 'E'}, { '\u00CB', 'E'}, { '\u00CC', 'I'}, { '\u00CD', 'I'}, { '\u00CE', 'I'},
    { '\u00CF', 'I'}, { '\u00D0', 'D'}, { '\u00D1', 'N'}, { '\u00D2', 'O'}, { '\u00D3', 'O'},
    { '\u00D4', 'O'}, { '\u00D5', 'O'}, { '\u00D6', 'O'}, { '\u00D8', 'O'}, { '\u00D9', 'U'},
    { '\u00DA', 'U'}, { '\u00DB', 'U'}, { '\u00DC', 'U'}, { '\u00DD', 'Y'}, { '\u00DE', 'd'},
    { '\u00DF', 's'}, { '\u00E0', 'a'}, { '\u00E1', 'a'}, { '\u00E2', 'a'}, { '\u00E3', 'a'},
    { '\u00E4', 'a'}, { '\u00E5', 'a'}, { '\u00E6', 'a'}, { '\u00E7', 'c'}, { '\u00E8', 'e'},
    { '\u00E9', 'e'}, { '\u00EA', 'e'}, { '\u00EB', 'e'}, { '\u00EC', 'i'}, { '\u00ED', 'i'},
    { '\u00EE', 'i'}, { '\u00EF', 'i'}, { '\u00F0', 'd'}, { '\u00F1', 'n'}, { '\u00F2', 'o'},
    { '\u00F3', 'o'}, { '\u00F4', 'o'}, { '\u00F5', 'o'}, { '\u00F6', 'o'}, { '\u00F8', 'o'},
    { '\u00F9', 'u'}, { '\u00FA', 'u'}, { '\u00FB', 'u'}, { '\u00FC', 'u'}, { '\u00FD', 'y'},
    { '\u00FE', 'd'}, { '\u00FF', 'y'}, { '\u0100', 'A'}, { '\u0101', 'a'}, { '\u0102', 'A'},
    { '\u0103', 'a'}, { '\u0104', 'A'}, { '\u0105', 'a'}, { '\u0106', 'C'}, { '\u0107', 'c'},
    { '\u0108', 'C'}, { '\u0109', 'c'}, { '\u010A', 'C'}, { '\u010B', 'c'}, { '\u010C', 'C'},
    { '\u010D', 'c'}, { '\u010E', 'D'}, { '\u010F', 'd'}, { '\u0110', 'D'}, { '\u0111', 'd'},
    { '\u0112', 'E'}, { '\u0113', 'e'}, { '\u0114', 'E'}, { '\u0115', 'e'}, { '\u0116', 'E'},
    { '\u0117', 'e'}, { '\u0118', 'E'}, { '\u0119', 'e'}, { '\u011A', 'E'}, { '\u011B', 'e'},
    { '\u011C', 'G'}, { '\u011D', 'g'}, { '\u011E', 'G'}, { '\u011F', 'g'}, { '\u0120', 'G'},
    { '\u0121', 'g'}, { '\u0122', 'G'}, { '\u0123', 'g'}, { '\u0124', 'H'}, { '\u0125', 'h'},
    { '\u0126', 'H'}, { '\u0127', 'h'}, { '\u0128', 'I'}, { '\u0129', 'i'}, { '\u012A', 'I'},
    { '\u012B', 'i'}, { '\u012C', 'I'}, { '\u012D', 'i'}, { '\u012E', 'I'}, { '\u012F', 'i'},
    { '\u0130', 'I'}, { '\u0131', 'i'}, { '\u0132', 'I'}, { '\u0133', 'i'}, { '\u0134', 'J'},
    { '\u0135', 'j'}, { '\u0136', 'K'}, { '\u0137', 'k'}, { '\u0138', 'k'}, { '\u0139', 'L'},
    { '\u013A', 'l'}, { '\u013B', 'L'}, { '\u013C', 'l'}, { '\u013D', 'L'}, { '\u013E', 'l'},
    { '\u013F', 'L'}, { '\u0140', 'l'}, { '\u0141', 'L'}, { '\u0142', 'l'}, { '\u0143', 'N'},
    { '\u0144', 'n'}, { '\u0145', 'N'}, { '\u0146', 'n'}, { '\u0147', 'N'}, { '\u0148', 'n'},
    { '\u0149', 'n'}, { '\u014A', 'N'}, { '\u014B', 'n'}, { '\u014C', 'O'}, { '\u014D', 'o'},
    { '\u014E', 'O'}, { '\u014F', 'o'}, { '\u0150', 'O'}, { '\u0151', 'o'}, { '\u0152', 'O'},
    { '\u0153', 'o'}, { '\u0154', 'R'}, { '\u0155', 'r'}, { '\u0156', 'R'}, { '\u0157', 'r'},
    { '\u0158', 'R'}, { '\u0159', 'r'}, { '\u015A', 'S'}, { '\u015B', 's'}, { '\u015C', 'S'},
    { '\u015D', 's'}, { '\u015E', 'S'}, { '\u015F', 's'}, { '\u0160', 'S'}, { '\u0161', 's'},
    { '\u0162', 'T'}, { '\u0163', 't'}, { '\u0164', 'T'}, { '\u0165', 't'}, { '\u0166', 'T'},
    { '\u0167', 't'}, { '\u0168', 'U'}, { '\u0169', 'u'}, { '\u016A', 'U'}, { '\u016B', 'u'},
    { '\u016C', 'U'}, { '\u016D', 'u'}, { '\u016E', 'U'}, { '\u016F', 'u'}, { '\u0170', 'U'},
    { '\u0171', 'u'}, { '\u0172', 'U'}, { '\u0173', 'u'}, { '\u0174', 'W'}, { '\u0175', 'w'},
    { '\u0176', 'Y'}, { '\u0177', 'y'}, { '\u0178', 'Y'}, { '\u0179', 'Z'}, { '\u017A', 'z'},
    { '\u017B', 'Z'}, { '\u017C', 'z'}, { '\u017D', 'Z'}, { '\u017E', 'z'}, { '\u01FA', 'A'},
    { '\u01FB', 'a'}, { '\u01FC', 'A'}, { '\u01FD', 'a'}, { '\u01FE', 'O'}, { '\u01FF', 'o'},
    { '\u0200', 'A'}, { '\u0201', 'a'}, { '\u0202', 'A'}, { '\u0203', 'a'}, { '\u0204', 'E'},
    { '\u0205', 'e'}, { '\u0206', 'E'}, { '\u0207', 'e'}, { '\u0208', 'I'}, { '\u0209', 'i'},
    { '\u020A', 'I'}, { '\u020B', 'i'}, { '\u020C', 'O'}, { '\u020D', 'o'}, { '\u020E', 'O'},
    { '\u020F', 'o'}, { '\u0210', 'R'}, { '\u0211', 'r'}, { '\u0212', 'R'}, { '\u0213', 'r'},
    { '\u0214', 'U'}, { '\u0215', 'u'}, { '\u0216', 'U'}, { '\u0217', 'u'}, { '\u0218', 'S'},
    { '\u0219', 's'}, { '\u021A', 'T'}, { '\u021B', 't'}, { '\u021E', 'H'}, { '\u021F', 'h'},
    { '\u0226', 'A'}, { '\u0227', 'a'}, { '\u0228', 'E'}, { '\u0229', 'e'}, { '\u022A', 'O'},
    { '\u022B', 'o'}, { '\u022C', 'O'}, { '\u022D', 'o'}, { '\u022E', 'O'}, { '\u022F', 'o'},
    { '\u0230', 'O'}, { '\u0231', 'o'}, { '\u0232', 'I'}, { '\u0233', 'i'},

    // Greek characters
    { '\u0386', '\u0391'}, { '\u0388', '\u0395'}, { '\u0389', '\u0397'}, { '\u038A', '\u0399'},
    { '\u038C', '\u039F'}, { '\u038E', '\u03A5'}, { '\u038F', '\u03A9'}, { '\u0390', '\u03B9'},
    { '\u03AA', '\u0399'}, { '\u03AB', '\u03A5'}, { '\u03AC', '\u03B1'}, { '\u03AD', '\u03B5'},
    { '\u03AE', '\u03B7'}, { '\u03AF', '\u03B9'}, { '\u03B0', '\u03C5'}, { '\u03CA', '\u03B9'},
    { '\u03CB', '\u03C5'}, { '\u03CC', '\u03BF'}, { '\u03CD', '\u03C5'}, { '\u03CE', '\u03C9'},
    { '\u03D3', '\u03A5'}, { '\u03D4', '\u03A5'},

    // Cyrillic characters
    { '\u0400', '\u0415'}, { '\u0401', '\u0415'}, { '\u0403', '\u0413'}, { '\u0407', '\u0406'},
    { '\u040C', '\u041A'}, { '\u040D', '\u0418'}, { '\u040E', '\u0423'}, { '\u0419', '\u0418'},
    { '\u0439', '\u0438'}, { '\u0450', '\u0435'}, { '\u0451', '\u0435'}, { '\u0453', '\u0433'},
    { '\u0457', '\u0456'}, { '\u045C', '\u043A'}, { '\u045D', '\u0438'}, { '\u045E', '\u0443'},
    { '\u0476', '\u0474'}, { '\u0477', '\u0475'}, { '\u04C1', '\u0416'}, { '\u04C2', '\u0436'},
    { '\u04D0', '\u0410'}, { '\u04D1', '\u0430'}, { '\u04D2', '\u0410'}, { '\u04D3', '\u0430'},
    { '\u04D6', '\u0415'}, { '\u04D7', '\u0435'}, { '\u04DA', '\u04D8'}, { '\u04DB', '\u04D9'},
    { '\u04DC', '\u0416'}, { '\u04DD', '\u0436'}, { '\u04DE', '\u0417'}, { '\u04DF', '\u0437'},
    { '\u04E2', '\u0418'}, { '\u04E3', '\u0438'}, { '\u04E4', '\u0418'}, { '\u04E5', '\u0438'},
    { '\u04E6', '\u041E'}, { '\u04E7', '\u043E'}, { '\u04EA', '\u04E8'}, { '\u04EB', '\u04E9'},
    { '\u04EC', '\u042D'}, { '\u04ED', '\u044D'}, { '\u04EE', '\u0423'}, { '\u04EF', '\u0443'},
    { '\u04F0', '\u0423'}, { '\u04F1', '\u0443'}, { '\u04F2', '\u0423'}, { '\u04F3', '\u0443'},
    { '\u04F4', '\u0427'}, { '\u04F5', '\u0447'}, { '\u04F8', '\u042B'}, { '\u04F9', '\u044B'},
  };

  /** Codepoint mappings (extended). */
  private static final char[][] NC1E00 = {
    // Extended characters
    { '\u1E00', 'A'}, { '\u1E01', 'a'}, { '\u1E02', 'B'}, { '\u1E03', 'b'}, { '\u1E04', 'B'},
    { '\u1E05', 'b'}, { '\u1E06', 'B'}, { '\u1E07', 'b'}, { '\u1E08', 'C'}, { '\u1E09', 'c'},
    { '\u1E0A', 'D'}, { '\u1E0B', 'd'}, { '\u1E0C', 'D'}, { '\u1E0D', 'd'}, { '\u1E0E', 'D'},
    { '\u1E0F', 'd'}, { '\u1E10', 'D'}, { '\u1E11', 'd'}, { '\u1E12', 'D'}, { '\u1E13', 'd'},
    { '\u1E14', 'E'}, { '\u1E15', 'e'}, { '\u1E16', 'E'}, { '\u1E17', 'e'}, { '\u1E18', 'E'},
    { '\u1E19', 'e'}, { '\u1E1A', 'E'}, { '\u1E1B', 'e'}, { '\u1E1C', 'E'}, { '\u1E1D', 'e'},
    { '\u1E1E', 'F'}, { '\u1E1F', 'f'}, { '\u1E20', 'G'}, { '\u1E21', 'g'}, { '\u1E22', 'H'},
    { '\u1E23', 'h'}, { '\u1E24', 'H'}, { '\u1E25', 'h'}, { '\u1E26', 'H'}, { '\u1E27', 'h'},
    { '\u1E28', 'H'}, { '\u1E29', 'h'}, { '\u1E2A', 'H'}, { '\u1E2B', 'h'}, { '\u1E2C', 'I'},
    { '\u1E2D', 'i'}, { '\u1E2E', 'I'}, { '\u1E2F', 'i'}, { '\u1E30', 'K'}, { '\u1E31', 'k'},
    { '\u1E32', 'K'}, { '\u1E33', 'k'}, { '\u1E34', 'K'}, { '\u1E35', 'k'}, { '\u1E36', 'L'},
    { '\u1E37', 'l'}, { '\u1E38', 'L'}, { '\u1E39', 'l'}, { '\u1E3A', 'L'}, { '\u1E3B', 'l'},
    { '\u1E3C', 'L'}, { '\u1E3D', 'l'}, { '\u1E3E', 'M'}, { '\u1E3F', 'm'}, { '\u1E40', 'M'},
    { '\u1E41', 'm'}, { '\u1E42', 'M'}, { '\u1E43', 'm'}, { '\u1E44', 'N'}, { '\u1E45', 'n'},
    { '\u1E46', 'N'}, { '\u1E47', 'n'}, { '\u1E48', 'N'}, { '\u1E49', 'n'}, { '\u1E4A', 'N'},
    { '\u1E4B', 'n'}, { '\u1E4C', 'O'}, { '\u1E4D', 'o'}, { '\u1E4E', 'O'}, { '\u1E4F', 'o'},
    { '\u1E50', 'O'}, { '\u1E51', 'o'}, { '\u1E52', 'O'}, { '\u1E53', 'o'}, { '\u1E54', 'P'},
    { '\u1E55', 'p'}, { '\u1E56', 'P'}, { '\u1E57', 'p'}, { '\u1E58', 'R'}, { '\u1E59', 'r'},
    { '\u1E5A', 'R'}, { '\u1E5B', 'r'}, { '\u1E5C', 'R'}, { '\u1E5D', 'r'}, { '\u1E5E', 'R'},
    { '\u1E5F', 'r'}, { '\u1E60', 'S'}, { '\u1E61', 's'}, { '\u1E62', 'S'}, { '\u1E63', 's'},
    { '\u1E64', 'S'}, { '\u1E65', 's'}, { '\u1E66', 'S'}, { '\u1E67', 's'}, { '\u1E68', 'S'},
    { '\u1E69', 's'}, { '\u1E6A', 'T'}, { '\u1E6B', 't'}, { '\u1E6C', 'T'}, { '\u1E6D', 't'},
    { '\u1E6E', 'T'}, { '\u1E6F', 't'}, { '\u1E70', 'T'}, { '\u1E71', 't'}, { '\u1E72', 'U'},
    { '\u1E73', 'u'}, { '\u1E74', 'U'}, { '\u1E75', 'u'}, { '\u1E76', 'U'}, { '\u1E77', 'u'},
    { '\u1E78', 'U'}, { '\u1E79', 'u'}, { '\u1E7A', 'U'}, { '\u1E7B', 'u'}, { '\u1E7C', 'V'},
    { '\u1E7D', 'v'}, { '\u1E7E', 'V'}, { '\u1E7F', 'v'}, { '\u1E80', 'W'}, { '\u1E81', 'w'},
    { '\u1E82', 'W'}, { '\u1E83', 'w'}, { '\u1E84', 'W'}, { '\u1E85', 'w'}, { '\u1E86', 'W'},
    { '\u1E87', 'w'}, { '\u1E88', 'W'}, { '\u1E89', 'w'}, { '\u1E8A', 'X'}, { '\u1E8B', 'x'},
    { '\u1E8C', 'X'}, { '\u1E8D', 'x'}, { '\u1E8E', 'Y'}, { '\u1E8F', 'y'}, { '\u1E90', 'Z'},
    { '\u1E91', 'z'}, { '\u1E92', 'Z'}, { '\u1E93', 'z'}, { '\u1E94', 'Z'}, { '\u1E95', 'z'},
    { '\u1E96', 'h'}, { '\u1E97', 't'}, { '\u1E98', 'w'}, { '\u1E99', 'y'}, { '\u1E9B', '\u017F'},
    { '\u1EA0', 'A'}, { '\u1EA1', 'a'}, { '\u1EA2', 'A'}, { '\u1EA3', 'a'}, { '\u1EA4', 'A'},
    { '\u1EA5', 'a'}, { '\u1EA6', 'A'}, { '\u1EA7', 'a'}, { '\u1EA8', 'A'}, { '\u1EA9', 'a'},
    { '\u1EAA', 'A'}, { '\u1EAB', 'a'}, { '\u1EAC', 'A'}, { '\u1EAD', 'a'}, { '\u1EAE', 'A'},
    { '\u1EAF', 'a'}, { '\u1EB0', 'A'}, { '\u1EB1', 'a'}, { '\u1EB2', 'A'}, { '\u1EB3', 'a'},
    { '\u1EB4', 'A'}, { '\u1EB5', 'a'}, { '\u1EB6', 'A'}, { '\u1EB7', 'a'}, { '\u1EB8', 'E'},
    { '\u1EB9', 'e'}, { '\u1EBA', 'E'}, { '\u1EBB', 'e'}, { '\u1EBC', 'E'}, { '\u1EBD', 'e'},
    { '\u1EBE', 'E'}, { '\u1EBF', 'e'}, { '\u1EC0', 'E'}, { '\u1EC1', 'e'}, { '\u1EC2', 'E'},
    { '\u1EC3', 'e'}, { '\u1EC4', 'E'}, { '\u1EC5', 'e'}, { '\u1EC6', 'E'}, { '\u1EC7', 'e'},
    { '\u1EC8', 'I'}, { '\u1EC9', 'i'}, { '\u1ECA', 'I'}, { '\u1ECB', 'i'}, { '\u1ECC', 'O'},
    { '\u1ECD', 'o'}, { '\u1ECE', 'O'}, { '\u1ECF', 'o'}, { '\u1ED0', 'O'}, { '\u1ED1', 'o'},
    { '\u1ED2', 'O'}, { '\u1ED3', 'o'}, { '\u1ED4', 'O'}, { '\u1ED5', 'o'}, { '\u1ED6', 'O'},
    { '\u1ED7', 'o'}, { '\u1ED8', 'O'}, { '\u1ED9', 'o'}, { '\u1EDA', 'O'}, { '\u1EDB', 'o'},
    { '\u1EDC', 'O'}, { '\u1EDD', 'o'}, { '\u1EDE', 'O'}, { '\u1EDF', 'o'}, { '\u1EE0', 'O'},
    { '\u1EE1', 'o'}, { '\u1EE2', 'O'}, { '\u1EE3', 'o'}, { '\u1EE4', 'U'}, { '\u1EE5', 'u'},
    { '\u1EE6', 'U'}, { '\u1EE7', 'u'}, { '\u1EE8', 'U'}, { '\u1EE9', 'u'}, { '\u1EEA', 'U'},
    { '\u1EEB', 'u'}, { '\u1EEC', 'U'}, { '\u1EED', 'u'}, { '\u1EEE', 'U'}, { '\u1EEF', 'u'},
    { '\u1EF0', 'U'}, { '\u1EF1', 'u'}, { '\u1EF2', 'Y'}, { '\u1EF3', 'y'}, { '\u1EF4', 'Y'},
    { '\u1EF5', 'y'}, { '\u1EF6', 'Y'}, { '\u1EF7', 'y'}, { '\u1EF8', 'Y'}, { '\u1EF9', 'y'},

    { '\u1F00', '\u03B1'}, { '\u1F01', '\u03B1'}, { '\u1F02', '\u03B1'}, { '\u1F03', '\u03B1'},
    { '\u1F04', '\u03B1'}, { '\u1F05', '\u03B1'}, { '\u1F06', '\u03B1'}, { '\u1F07', '\u03B1'},
    { '\u1F08', '\u0391'}, { '\u1F09', '\u0391'}, { '\u1F0A', '\u0391'}, { '\u1F0B', '\u0391'},
    { '\u1F0C', '\u0391'}, { '\u1F0D', '\u0391'}, { '\u1F0E', '\u0391'}, { '\u1F0F', '\u0391'},
    { '\u1F10', '\u03B5'}, { '\u1F11', '\u03B5'}, { '\u1F12', '\u03B5'}, { '\u1F13', '\u03B5'},
    { '\u1F14', '\u03B5'}, { '\u1F15', '\u03B5'}, { '\u1F18', '\u0395'}, { '\u1F19', '\u0395'},
    { '\u1F1A', '\u0395'}, { '\u1F1B', '\u0395'}, { '\u1F1C', '\u0395'}, { '\u1F1D', '\u0395'},
    { '\u1F20', '\u03B7'}, { '\u1F21', '\u03B7'}, { '\u1F22', '\u03B7'}, { '\u1F23', '\u03B7'},
    { '\u1F24', '\u03B7'}, { '\u1F25', '\u03B7'}, { '\u1F26', '\u03B7'}, { '\u1F27', '\u03B7'},
    { '\u1F28', '\u0397'}, { '\u1F29', '\u0397'}, { '\u1F2A', '\u0397'}, { '\u1F2B', '\u0397'},
    { '\u1F2C', '\u0397'}, { '\u1F2D', '\u0397'}, { '\u1F2E', '\u0397'}, { '\u1F2F', '\u0397'},
    { '\u1F30', '\u03B9'}, { '\u1F31', '\u03B9'}, { '\u1F32', '\u03B9'}, { '\u1F33', '\u03B9'},
    { '\u1F34', '\u03B9'}, { '\u1F35', '\u03B9'}, { '\u1F36', '\u03B9'}, { '\u1F37', '\u03B9'},
    { '\u1F38', '\u0399'}, { '\u1F39', '\u0399'}, { '\u1F3A', '\u0399'}, { '\u1F3B', '\u0399'},
    { '\u1F3C', '\u0399'}, { '\u1F3D', '\u0399'}, { '\u1F3E', '\u0399'}, { '\u1F3F', '\u0399'},
    { '\u1F40', '\u03BF'}, { '\u1F41', '\u03BF'}, { '\u1F42', '\u03BF'}, { '\u1F43', '\u03BF'},
    { '\u1F44', '\u03BF'}, { '\u1F45', '\u03BF'}, { '\u1F48', '\u039F'}, { '\u1F49', '\u039F'},
    { '\u1F4A', '\u039F'}, { '\u1F4B', '\u039F'}, { '\u1F4C', '\u039F'}, { '\u1F4D', '\u039F'},
    { '\u1F50', '\u03C5'}, { '\u1F51', '\u03C5'}, { '\u1F52', '\u03C5'}, { '\u1F53', '\u03C5'},
    { '\u1F54', '\u03C5'}, { '\u1F55', '\u03C5'}, { '\u1F56', '\u03C5'}, { '\u1F57', '\u03C5'},
    { '\u1F59', '\u03A5'}, { '\u1F5B', '\u03A5'}, { '\u1F5D', '\u03A5'}, { '\u1F5F', '\u03A5'},
    { '\u1F60', '\u03C9'}, { '\u1F61', '\u03C9'}, { '\u1F62', '\u03C9'}, { '\u1F63', '\u03C9'},
    { '\u1F64', '\u03C9'}, { '\u1F65', '\u03C9'}, { '\u1F66', '\u03C9'}, { '\u1F67', '\u03C9'},
    { '\u1F68', '\u03A9'}, { '\u1F69', '\u03A9'}, { '\u1F6A', '\u03A9'}, { '\u1F6B', '\u03A9'},
    { '\u1F6C', '\u03A9'}, { '\u1F6D', '\u03A9'}, { '\u1F6E', '\u03A9'}, { '\u1F6F', '\u03A9'},
    { '\u1F70', '\u03B1'}, { '\u1F72', '\u03B5'}, { '\u1F74', '\u03B7'}, { '\u1F76', '\u03B9'},
    { '\u1F78', '\u03BF'}, { '\u1F7A', '\u03C5'}, { '\u1F7C', '\u03C9'}, { '\u1F80', '\u03B1'},
    { '\u1F81', '\u03B1'}, { '\u1F82', '\u03B1'}, { '\u1F83', '\u03B1'}, { '\u1F84', '\u03B1'},
    { '\u1F85', '\u03B1'}, { '\u1F86', '\u03B1'}, { '\u1F87', '\u03B1'}, { '\u1F88', '\u0391'},
    { '\u1F89', '\u0391'}, { '\u1F8A', '\u0391'}, { '\u1F8B', '\u0391'}, { '\u1F8C', '\u0391'},
    { '\u1F8D', '\u0391'}, { '\u1F8E', '\u0391'}, { '\u1F8F', '\u0391'}, { '\u1F90', '\u03B7'},
    { '\u1F91', '\u03B7'}, { '\u1F92', '\u03B7'}, { '\u1F93', '\u03B7'}, { '\u1F94', '\u03B7'},
    { '\u1F95', '\u03B7'}, { '\u1F96', '\u03B7'}, { '\u1F97', '\u03B7'}, { '\u1F98', '\u0397'},
    { '\u1F99', '\u0397'}, { '\u1F9A', '\u0397'}, { '\u1F9B', '\u0397'}, { '\u1F9C', '\u0397'},
    { '\u1F9D', '\u0397'}, { '\u1F9E', '\u0397'}, { '\u1F9F', '\u0397'}, { '\u1FA0', '\u03C9'},
    { '\u1FA1', '\u03C9'}, { '\u1FA2', '\u03C9'}, { '\u1FA3', '\u03C9'}, { '\u1FA4', '\u03C9'},
    { '\u1FA5', '\u03C9'}, { '\u1FA6', '\u03C9'}, { '\u1FA7', '\u03C9'}, { '\u1FA8', '\u03A9'},
    { '\u1FA9', '\u03A9'}, { '\u1FAA', '\u03A9'}, { '\u1FAB', '\u03A9'}, { '\u1FAC', '\u03A9'},
    { '\u1FAD', '\u03A9'}, { '\u1FAE', '\u03A9'}, { '\u1FAF', '\u03A9'}, { '\u1FB0', '\u03B1'},
    { '\u1FB1', '\u03B1'}, { '\u1FB2', '\u03B1'}, { '\u1FB3', '\u03B1'}, { '\u1FB4', '\u03B1'},
    { '\u1FB6', '\u03B1'}, { '\u1FB7', '\u03B1'}, { '\u1FB8', '\u0391'}, { '\u1FB9', '\u0391'},
    { '\u1FBA', '\u0391'}, { '\u1FBC', '\u0391'}, { '\u1FC1', '\u00A8'}, { '\u1FC2', '\u03B7'},
    { '\u1FC3', '\u03B7'}, { '\u1FC4', '\u03B7'}, { '\u1FC6', '\u03B7'}, { '\u1FC7', '\u03B7'},
    { '\u1FC8', '\u0395'}, { '\u1FCA', '\u0397'}, { '\u1FCC', '\u0397'}, { '\u1FCD', '\u1FBF'},
    { '\u1FCE', '\u1FBF'}, { '\u1FCF', '\u1FBF'}, { '\u1FD0', '\u03B9'}, { '\u1FD1', '\u03B9'},
    { '\u1FD2', '\u03B9'}, { '\u1FD6', '\u03B9'}, { '\u1FD7', '\u03B9'}, { '\u1FD8', '\u0399'},
    { '\u1FD9', '\u0399'}, { '\u1FDA', '\u0399'}, { '\u1FDD', '\u1FFE'}, { '\u1FDE', '\u1FFE'},
    { '\u1FDF', '\u1FFE'}, { '\u1FE0', '\u03C5'}, { '\u1FE1', '\u03C5'}, { '\u1FE2', '\u03C5'},
    { '\u1FE4', '\u03C1'}, { '\u1FE5', '\u03C1'}, { '\u1FE6', '\u03C5'}, { '\u1FE7', '\u03C5'},
    { '\u1FE8', '\u03A5'}, { '\u1FE9', '\u03A5'}, { '\u1FEA', '\u03A5'}, { '\u1FEC', '\u03A1'},
    { '\u1FED', '\u00A8'}, { '\u1FF2', '\u03C9'}, { '\u1FF3', '\u03C9'}, { '\u1FF4', '\u03C9'},
    { '\u1FF6', '\u03C9'}, { '\u1FF7', '\u03C9'}, { '\u1FF8', '\u039F'}, { '\u1FFA', '\u03A9'},
    { '\u1FFC', '\u03A9'}
  };

  /** Codepoint mappings (additional). */
  private static final char[][] NCXXXX = {
    { '\u0622', '\u0627'}, { '\u0623', '\u0627'}, { '\u0624', '\u0648'}, { '\u0625', '\u0627'},
    { '\u0626', '\u064A'}, { '\u06C0', '\u06D5'}, { '\u06C2', '\u06C1'}, { '\u06D3', '\u06D2'},
    { '\u0929', '\u0928'}, { '\u0931', '\u0930'}, { '\u0934', '\u0933'}, { '\u0958', '\u0915'},
    { '\u0959', '\u0916'}, { '\u095A', '\u0917'}, { '\u095B', '\u091C'}, { '\u095C', '\u0921'},
    { '\u095D', '\u0922'}, { '\u095E', '\u092B'}, { '\u095F', '\u092F'}, { '\u09CB', '\u09C7'},
    { '\u09CC', '\u09C7'}, { '\u09DC', '\u09A1'}, { '\u09DD', '\u09A2'}, { '\u09DF', '\u09AF'},
    { '\u0A33', '\u0A32'}, { '\u0A36', '\u0A38'}, { '\u0A59', '\u0A16'}, { '\u0A5A', '\u0A17'},
    { '\u0A5B', '\u0A1C'}, { '\u0A5E', '\u0A2B'}, { '\u0B48', '\u0B47'}, { '\u0B4B', '\u0B47'},
    { '\u0B4C', '\u0B47'}, { '\u0B5C', '\u0B21'}, { '\u0B5D', '\u0B22'}, { '\u0B94', '\u0B92'},
    { '\u0BCA', '\u0BC6'}, { '\u0BCB', '\u0BC7'}, { '\u0BCC', '\u0BC6'}, { '\u0C48', '\u0C46'},
    { '\u0CC0', '\u0CBF'}, { '\u0CC7', '\u0CC6'}, { '\u0CC8', '\u0CC6'}, { '\u0CCA', '\u0CC6'},
    { '\u0CCB', '\u0CC6'}, { '\u0D4A', '\u0D46'}, { '\u0D4B', '\u0D47'}, { '\u0D4C', '\u0D46'},
    { '\u0DDA', '\u0DD9'}, { '\u0DDC', '\u0DD9'}, { '\u0DDD', '\u0DD9'}, { '\u0DDE', '\u0DD9'},
    { '\u0F43', '\u0F42'}, { '\u0F4D', '\u0F4C'}, { '\u0F52', '\u0F51'}, { '\u0F57', '\u0F56'},
    { '\u0F5C', '\u0F5B'}, { '\u0F69', '\u0F40'}, { '\u0F73', '\u0F71'}, { '\u0F75', '\u0F71'},
    { '\u0F76', '\u0FB2'}, { '\u0F78', '\u0FB3'}, { '\u0F81', '\u0F71'}, { '\u0F93', '\u0F92'},
    { '\u0F9D', '\u0F9C'}, { '\u0FA2', '\u0FA1'}, { '\u0FA7', '\u0FA6'}, { '\u0FAC', '\u0FAB'},
    { '\u0FB9', '\u0F90'}, { '\u1026', '\u1025'}, { '\u219A', '\u2190'}, { '\u219B', '\u2192'},
    { '\u21AE', '\u2194'}, { '\u21CD', '\u21D0'}, { '\u21CE', '\u21D4'}, { '\u21CF', '\u21D2'},
    { '\u2204', '\u2203'}, { '\u2209', '\u2208'}, { '\u220C', '\u220B'}, { '\u2224', '\u2223'},
    { '\u2226', '\u2225'}, { '\u2241', '\u223C'}, { '\u2244', '\u2243'}, { '\u2247', '\u2245'},
    { '\u2249', '\u2248'}, { '\u2260', '\u003D'}, { '\u2262', '\u2261'}, { '\u226D', '\u224D'},
    { '\u226E', '\u003C'}, { '\u226F', '\u003E'}, { '\u2270', '\u2264'}, { '\u2271', '\u2265'},
    { '\u2274', '\u2272'}, { '\u2275', '\u2273'}, { '\u2278', '\u2276'}, { '\u2279', '\u2277'},
    { '\u2280', '\u227A'}, { '\u2281', '\u227B'}, { '\u2284', '\u2282'}, { '\u2285', '\u2283'},
    { '\u2288', '\u2286'}, { '\u2289', '\u2287'}, { '\u22AC', '\u22A2'}, { '\u22AD', '\u22A8'},
    { '\u22AE', '\u22A9'}, { '\u22AF', '\u22AB'}, { '\u22E0', '\u227C'}, { '\u22E1', '\u227D'},
    { '\u22E2', '\u2291'}, { '\u22E3', '\u2292'}, { '\u22EA', '\u22B2'}, { '\u22EB', '\u22B3'},
    { '\u22EC', '\u22B4'}, { '\u22ED', '\u22B5'}, { '\u2ADC', '\u2ADD'}, { '\u304C', '\u304B'},
    { '\u304E', '\u304D'}, { '\u3050', '\u304F'}, { '\u3052', '\u3051'}, { '\u3054', '\u3053'},
    { '\u3056', '\u3055'}, { '\u3058', '\u3057'}, { '\u305A', '\u3059'}, { '\u305C', '\u305B'},
    { '\u305E', '\u305D'}, { '\u3060', '\u305F'}, { '\u3062', '\u3061'}, { '\u3065', '\u3064'},
    { '\u3067', '\u3066'}, { '\u3069', '\u3068'}, { '\u3070', '\u306F'}, { '\u3071', '\u306F'},
    { '\u3073', '\u3072'}, { '\u3074', '\u3072'}, { '\u3076', '\u3075'}, { '\u3077', '\u3075'},
    { '\u3079', '\u3078'}, { '\u307A', '\u3078'}, { '\u307C', '\u307B'}, { '\u307D', '\u307B'},
    { '\u3094', '\u3046'}, { '\u309E', '\u309D'}, { '\u30AC', '\u30AB'}, { '\u30AE', '\u30AD'},
    { '\u30B0', '\u30AF'}, { '\u30B2', '\u30B1'}, { '\u30B4', '\u30B3'}, { '\u30B6', '\u30B5'},
    { '\u30B8', '\u30B7'}, { '\u30BA', '\u30B9'}, { '\u30BC', '\u30BB'}, { '\u30BE', '\u30BD'},
    { '\u30C0', '\u30BF'}, { '\u30C2', '\u30C1'}, { '\u30C5', '\u30C4'}, { '\u30C7', '\u30C6'},
    { '\u30C9', '\u30C8'}, { '\u30D0', '\u30CF'}, { '\u30D1', '\u30CF'}, { '\u30D3', '\u30D2'},
    { '\u30D4', '\u30D2'}, { '\u30D6', '\u30D5'}, { '\u30D7', '\u30D5'}, { '\u30D9', '\u30D8'},
    { '\u30DA', '\u30D8'}, { '\u30DC', '\u30DB'}, { '\u30DD', '\u30DB'}, { '\u30F4', '\u30A6'},
    { '\u30F7', '\u30EF'}, { '\u30F8', '\u30F0'}, { '\u30F9', '\u30F1'}, { '\u30FA', '\u30F2'},
    { '\u30FE', '\u30FD'}, { '\uFB1D', '\u05D9'}, { '\uFB1F', '\u05F2'}, { '\uFB2A', '\u05E9'},
    { '\uFB2B', '\u05E9'}, { '\uFB2C', '\u05E9'}, { '\uFB2D', '\u05E9'}, { '\uFB2E', '\u05D0'},
    { '\uFB2F', '\u05D0'}, { '\uFB30', '\u05D0'}, { '\uFB31', '\u05D1'}, { '\uFB32', '\u05D2'},
    { '\uFB33', '\u05D3'}, { '\uFB34', '\u05D4'}, { '\uFB35', '\u05D5'}, { '\uFB36', '\u05D6'},
    { '\uFB38', '\u05D8'}, { '\uFB39', '\u05D9'}, { '\uFB3A', '\u05DA'}, { '\uFB3B', '\u05DB'},
    { '\uFB3C', '\u05DC'}, { '\uFB3E', '\u05DE'}, { '\uFB40', '\u05E0'}, { '\uFB41', '\u05E1'},
    { '\uFB43', '\u05E3'}, { '\uFB44', '\u05E4'}, { '\uFB46', '\u05E6'}, { '\uFB47', '\u05E7'},
    { '\uFB48', '\u05E8'}, { '\uFB49', '\u05E9'}, { '\uFB4A', '\u05EA'}, { '\uFB4B', '\u05D5'},
    { '\uFB4C', '\u05D1'}, { '\uFB4D', '\u05DB'}, { '\uFB4E', '\u05E4'}
  };

  // lazy initialization
  static {
    char[] norm = new char[0x440];
    for(int n = 0; n < 0x440; n++) norm[n] = (char) (n + 0xC0);
    for(final char[] aNC : NC0000) norm[aNC[0] - 0xC0] = aNC[1];
    NORM_0000 = norm;

    norm = new char[0x200];
    for(int n = 0; n < 0x200; n++) norm[n] = (char) (n + 1.0E00);
    for(final char[] aNC : NC1E00) norm[aNC[0] - 0x1E00] = aNC[1];
    NORM_1E00 = norm;

    final IntMap map = new IntMap();
    for(final char[] aNC : NCXXXX) map.put(aNC[0], aNC[1]);
    NORM_XXXX = map;
  }

  /** Mapping table for codepoints from 0000-0500. */
  private static final char[] NORM_0000;
  /** Mapping table for codepoints from 1E00-2000. */
  private static final char[] NORM_1E00;
  /** Additionally mapped codepoints. */
  private static final IntMap NORM_XXXX;
}
