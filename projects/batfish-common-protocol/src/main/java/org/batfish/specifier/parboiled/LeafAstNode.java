package org.batfish.specifier.parboiled;

import java.util.Objects;

class LeafAstNode extends AstNode {

  private Object _value;

  LeafAstNode(Object value) {
    super(null, null);
    this._value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LeafAstNode)) {
      return false;
    }
    return Objects.equals(_value, ((LeafAstNode) o)._value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_value);
  }

  public Object getValue() {
    return _value;
  }

  @Override
  public String toString() {
    return Objects.toString(_value);
  }
}
