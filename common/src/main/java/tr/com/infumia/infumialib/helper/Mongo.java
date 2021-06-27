/*
 * This file is part of helper, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package tr.com.infumia.infumialib.helper;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an individual Mongo datasource, created by the library.
 */
public interface Mongo extends AutoCloseable {

  @Override
  default void close() throws Exception {
    this.getClient().close();
  }

  /**
   * Gets the client instance backing the datasource
   *
   * @return the client instance
   */
  @NotNull
  MongoClient getClient();

  /**
   * Gets a specific database instance
   *
   * @param name the name of the database
   *
   * @return the database
   */
  @NotNull
  default MongoDatabase getDatabase(@NotNull final String name) {
    return this.getClient().getDatabase(name);
  }

  /**
   * Gets the main database in use by the instance.
   *
   * @return the main database
   */
  @NotNull
  MongoDatabase getDatabase();

  /**
   * Gets the main Morphia datastore in use by the instance
   *
   * @return the main datastore
   */
  @NotNull
  Datastore getMorphiaDatastore();
}
