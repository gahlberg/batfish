package org.batfish.specifier.parboiled;

final class UnionFilterAstNode extends SetOpFilterAstNode {

  UnionFilterAstNode(FilterAstNode left, FilterAstNode right) {
    _left = left;
    _right = right;
  }

  @Override
  public <T> T accept(AstNodeVisitor<T> visitor) {
    return visitor.visitUnionFilterAstNode(this);
  }

  @Override
  public <T> T accept(FilterAstNodeVisitor<T> visitor) {
    return visitor.visitUnionFilterAstNode(this);
  }
}
