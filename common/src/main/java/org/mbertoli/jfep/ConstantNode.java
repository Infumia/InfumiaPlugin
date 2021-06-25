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
 * <p><b>Name:</b> ConstantNode</p>
 * <p><b>Description:</b>
 * A constant value node
 * </p>
 * <p><b>Date:</b> 08/dic/06
 * <b>Time:</b> 15:35:24</p>
 *
 * @author Bertoli Marco
 * @version 1.0
 */
public class ConstantNode implements ExpressionNode {

  /**
   * List of built-in constant names
   */
  public static final String[] CONSTANTS = new String[]{"pi", "e"};

  /**
   * List of built-in constant values
   */
  public static final double[] VALUES = new double[]{Math.PI, Math.E};

  /**
   * An empty array with children
   */
  protected ExpressionNode[] children = new ExpressionNode[0];

  /**
   * Value of the constant
   */
  protected double constant;

  /**
   * Name of the constant. Only if it's built-in
   */
  protected String name;

  /**
   * Builds a constant node
   *
   * @param constant constant to be put in node
   */
  public ConstantNode(final double constant) {
    this.constant = constant;
    this.name = null;
  }

  /**
   * Builds a constant node, with an unique constant
   *
   * @param name name of the constant in the CONSTANTS array
   */
  public ConstantNode(final String name) {
    this.name = name;
    for (int i = 0; i < ConstantNode.CONSTANTS.length; i++) {
      if (ConstantNode.CONSTANTS[i].equals(name)) {
        this.constant = ConstantNode.VALUES[i];
        return;
      }
    }
    throw new IllegalArgumentException("Unrecognized constant");
  }

  /**
   * Builds a constant node, with an unique constant
   *
   * @param pos position of the constant in the CONSTANTS array
   *
   * @see ConstantNode#CONSTANTS
   */
  public ConstantNode(final int pos) {
    this.name = ConstantNode.CONSTANTS[pos];
    this.constant = ConstantNode.VALUES[pos];
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Object clone() {
    return new ConstantNode(this.constant);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    if (this.name == null) {
      return this.getSubtype();
    } else {
      return this.name;
    }
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#count()
   */
  @Override
  public int count() {
    return 1;
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
    return 1; // This is a leaf node
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#getSubtype()
   */
  @Override
  public String getSubtype() {
    // Checks if this is integer or double
    if (Math.floor(this.constant) == this.constant) {
      return Long.toString(Math.round(this.constant));
    } else {
      return Double.toString(this.constant);
    }
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#getType()
   */
  @Override
  public int getType() {
    return ExpressionNode.CONSTANT_NODE;
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#getValue()
   */
  @Override
  public double getValue() {
    return this.constant;
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#setVariable(java.lang.String, double)
   */
  @Override
  public void setVariable(final String name, final double value) {
    // Nothing to be done here...
  }
}
