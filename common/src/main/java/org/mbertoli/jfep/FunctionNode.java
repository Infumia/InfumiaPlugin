/**
 * Copyright 2006 Bertoli Marco
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mbertoli.jfep;

/**
 * <p><b>Name:</b> FunctionNode</p>
 * <p><b>Description:</b>
 * This node is used to evaluate every kind of function with a single parameter, like abs(...) or sin(...)
 * </p>
 * <p><b>Date:</b> 08/dic/06
 * <b>Time:</b> 16:31:14</p>
 *
 * @author Bertoli Marco
 * @version 1.0
 */
public class FunctionNode implements ExpressionNode {

  /**
   * List of supported functions
   */
  public static final String[] FUNCTIONS = new String[]{"-", "sin", "cos", "tan",
    "asin", "acos", "atan", "sinh", "cosh", "tanh", "asinh", "acosh", "atanh",
    "ln", "log", "abs", "rand", "sqrt", "erf", "erfc", "gamma", "exp", "cot", "log2"};

  /**
   * Child node
   */
  protected ExpressionNode child;

  /**
   * An array with children
   */
  protected ExpressionNode[] children;

  /**
   * Function of this node
   */
  protected int function;

  /**
   * Creates a function node.
   *
   * @param child child node of this node
   * @param function function to be evaluated. This is the index in <code>FUNCTIONS</code> array
   *
   * @see FunctionNode#FUNCTIONS
   */
  public FunctionNode(final ExpressionNode child, final int function) {
    this.child = child;
    this.function = function;
    this.children = new ExpressionNode[]{child};
  }

  /**
   * Creates a function node.
   *
   * @param child child node of this node
   * @param function name of function to be evaluated.
   *
   * @throws IllegalArgumentException if function is unsupported
   */
  public FunctionNode(final ExpressionNode child, final String function) throws IllegalArgumentException {
    this.child = child;
    this.function = -1;
    this.children = new ExpressionNode[]{child};
    for (int i = 0; i < FunctionNode.FUNCTIONS.length; i++) {
      if (FunctionNode.FUNCTIONS[i].equals(function)) {
        this.function = i;
        break;
      }
    }
    if (this.function < 0) {
      throw new IllegalArgumentException("Unrecognized function");
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Object clone() {
    final ExpressionNode n_child = (ExpressionNode) this.child.clone();
    return new FunctionNode(n_child, this.function);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    // Special case for negation function
    if (this.function != 0) {
      return this.getSubtype() + "(" + this.child.toString() + ")";
    } else {
      if (this.child.getType() == ExpressionNode.CONSTANT_NODE || this.child.getType() == ExpressionNode.VARIABLE_NODE ||
        this.child.getType() == ExpressionNode.FUNCTION_NODE && !this.child.getSubtype().equals(FunctionNode.FUNCTIONS[0])) {
        return FunctionNode.FUNCTIONS[0] + this.child.toString();
      } else {
        return FunctionNode.FUNCTIONS[0] + "(" + this.child.toString() + ")";
      }
    }
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#count()
   */
  @Override
  public int count() {
    return 1 + this.child.count();
  }

  /* (non-Javadoc)
   * @see org.mbertoli.jfep.ExpressionNode#getChildrenNodes()
   */
  @Override
  public ExpressionNode[] getChildrenNodes() {
    return this.children;
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#getDepth()
   */
  @Override
  public int getDepth() {
    return 1 + this.child.getDepth();
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#getSubtype()
   */
  @Override
  public String getSubtype() {
    return FunctionNode.FUNCTIONS[this.function];
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#getType()
   */
  @Override
  public int getType() {
    return ExpressionNode.FUNCTION_NODE;
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#getValue()
   */
  @Override
  public double getValue() {
    switch (this.function) {
      case 0:
        return -this.child.getValue();
      case 1:
        return Math.sin(this.child.getValue());
      case 2:
        return Math.cos(this.child.getValue());
      case 3:
        return Math.tan(this.child.getValue());
      case 4:
        return Math.asin(this.child.getValue());
      case 5:
        return Math.acos(this.child.getValue());
      case 6:
        return Math.atan(this.child.getValue());
      case 7:
        return Sfun.sinh(this.child.getValue());
      case 8:
        return Sfun.cosh(this.child.getValue());
      case 9:
        return Sfun.tanh(this.child.getValue());
      case 10:
        return Sfun.asinh(this.child.getValue());
      case 11:
        return Sfun.acosh(this.child.getValue());
      case 12:
        return Sfun.atanh(this.child.getValue());
      case 13:
        return Math.log(this.child.getValue());
      case 14:
        return Math.log(this.child.getValue()) * 0.43429448190325182765;
      case 15:
        return Math.abs(this.child.getValue());
      case 16:
        return Math.random() * this.child.getValue();
      case 17:
        return Math.sqrt(this.child.getValue());
      case 18:
        return Sfun.erf(this.child.getValue());
      case 19:
        return Sfun.erfc(this.child.getValue());
      case 20:
        return Sfun.gamma(this.child.getValue());
      case 21:
        return Math.exp(this.child.getValue());
      case 22:
        return Sfun.cot(this.child.getValue());
      case 23:
        return Math.log(this.child.getValue()) * 1.442695040888963407360;
    }
    // This is never reached
    return 0;
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#setVariable(java.lang.String, double)
   */
  @Override
  public void setVariable(final String name, final double value) {
    this.child.setVariable(name, value);
  }
}
