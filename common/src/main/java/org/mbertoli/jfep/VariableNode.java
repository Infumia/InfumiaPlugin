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
 * <p><b>Name:</b> VariableNode</p>
 * <p><b>Description:</b>
 * A node holding a double variable.
 * </p>
 * <p><b>Date:</b> 08/dic/06
 * <b>Time:</b> 15:56:59</p>
 *
 * @author Bertoli Marco
 * @version 1.0
 */
public class VariableNode implements ExpressionNode {

  /**
   * An empty array with children
   */
  protected ExpressionNode[] children = new ExpressionNode[0];

  /**
   * True if variable was not initialized
   */
  protected boolean error;

  /**
   * Name of the variable
   */
  protected String name;

  /**
   * Value of the variable
   */
  protected double value;

  /**
   * Creates a new variable node with given name.
   *
   * @param name name of the variable
   * @param error throws an exception if value is get but variable
   *   is not initialized. Otherwise 0.0 is returned.
   */
  public VariableNode(final String name, final boolean error) {
    this.name = name;
    this.value = 0.0;
    this.error = error;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Object clone() {
    final VariableNode node = new VariableNode(this.name, this.error);
    node.value = this.value;
    return node;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return this.getSubtype();
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
    return this.name;
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#getType()
   */
  @Override
  public int getType() {
    return ExpressionNode.VARIABLE_NODE;
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#getValue()
   */
  @Override
  public double getValue() {
    if (!this.error) {
      return this.value;
    } else {
      throw new EvaluationException("Variable '" + this.name + "' was not initialized.");
    }
  }

  /* (non-Javadoc)
   * @see jmt.engine.math.parser.ExpressionNode#setVariable(java.lang.String, double)
   */
  @Override
  public void setVariable(final String name, final double value) {
    if (this.name.equals(name)) {
      this.value = value;
      this.error = false;
    }
  }
}
