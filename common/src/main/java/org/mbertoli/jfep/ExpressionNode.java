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
 * <p><b>Name:</b> ExpressionNode</p>
 * <p><b>Description:</b>
 * Common interface implemented by different nodes of an expression tree
 * </p>
 * <p><b>Date:</b> 08/dic/06
 * <b>Time:</b> 14:28:56</p>
 *
 * @author Bertoli Marco
 * @version 1.0
 */
public interface ExpressionNode extends Cloneable {

  int CONSTANT_NODE = 0;

  int FUNCTION_NODE = 3;

  int OPERATOR_NODE = 2;

  int VARIABLE_NODE = 1;

  /**
   * Clones current node
   *
   * @return deep copy of current node
   */
  Object clone();

  /**
   * Returns a string describing the entire tree
   *
   * @return string describing the entire tree
   */
  String toString();

  /**
   * Counts number of nodes in current subtree
   *
   * @return number of nodes (included root)
   */
  int count();

  /**
   * Returns children nodes of this node, ordered from leftmost to rightmost.
   *
   * @return an array of children nodes. If node has no child, destination array has zero size.
   */
  ExpressionNode[] getChildrenNodes();

  /**
   * Returns depth of current subtree
   *
   * @return depth of the tree
   */
  int getDepth();

  /**
   * Returns more information on node type
   *
   * @return name of node function or operator symbol
   */
  String getSubtype();

  /**
   * Returns type of node
   *
   * @return CONSTANT_NODE, VARIABLE_NODE, OPERATOR_NODE, FUNCTION_NODE
   */
  int getType();

  /**
   * Returns value of target node
   *
   * @return value of node
   */
  double getValue();

  /**
   * Sets the value for a variable
   *
   * @param name name of the variable to be set
   * @param value value for the variable
   */
  void setVariable(String name, double value);
}
