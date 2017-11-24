package org.basex.query.func.fn;

import static org.basex.util.Token.*;

import org.basex.io.serial.*;
import org.basex.query.*;
import org.basex.query.expr.*;
import org.basex.query.func.*;
import org.basex.query.iter.*;
import org.basex.query.value.*;
import org.basex.query.value.item.*;
import org.basex.query.value.seq.*;
import org.basex.util.*;

/**
 * Function implementation.
 *
 * @author BaseX Team 2005-17, BSD License
 * @author Christian Gruen
 */
public class FnTrace extends StandardFunc {
  @Override
  public final Iter iter(final QueryContext qc) throws QueryException {
    return value(qc).iter();
  }

  @Override
  public Value value(final QueryContext qc) throws QueryException {
    final Value value = qc.value(exprs[0]);
    final byte[] label = exprs.length > 1 ? toToken(exprs[1], qc) : null;

    if(value.isEmpty() || value instanceof RangeSeq || value instanceof SingletonSeq) {
      trace(token(value.toString()), label, qc);
    } else {
      try {
        for(final Item it : value) {
          qc.checkStop();
          trace(it.serialize(SerializerMode.DEBUG.get()).finish(), label, qc);
        }
      } catch(final QueryIOException ex) {
        throw ex.getCause(info);
      }
    }
    return value;
  }

  @Override
  protected Expr opt(final CompileContext cc) {
    return adoptType(exprs[0]);
  }

  /**
   * Dumps the specified info to standard error or the info view of the GUI.
   * @param value traced value
   * @param label additional label to display (may be {@code null})
   * @param qc query context
   */
  public static void trace(final byte[] value, final byte[] label, final QueryContext qc) {
    final TokenBuilder tb = new TokenBuilder();
    if(label != null) tb.add(label);
    tb.add(value);
    qc.jc().tracer.print(tb.toString(), qc);
  }
}
