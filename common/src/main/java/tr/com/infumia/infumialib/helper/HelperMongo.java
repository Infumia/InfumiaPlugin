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
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public final class HelperMongo implements Mongo {

  @NotNull
  private final MongoClient client;

  @NotNull
  private final MongoDatabase database;

  @NotNull
  private final Datastore morphiaDatastore;

  public HelperMongo(@NotNull final ClassLoader classLoader, @NotNull final MongoDatabaseCredentials credentials,
                     @NotNull final Class<?>... classes) {
    final var authParams = !credentials.getUsername().isEmpty() && !credentials.getPassword().isEmpty()
      ? credentials.getUsername() + ":" + credentials.getPassword() + "@"
      : "";
    final var authSource = !credentials.getAuthSource().isEmpty()
      ? "/?authSource=" + credentials.getAuthSource()
      : "";
    final var uri = !credentials.getUri().isEmpty()
      ? credentials.getUri()
      : "mongodb://" + authParams + credentials.getHost() + ":" + credentials.getPort() + authSource;
    this.client = MongoClients.create(uri);
    this.database = this.getDatabase(credentials.getDatabase());
    this.morphiaDatastore = this.getMorphiaDatastore(classLoader, credentials.getDatabase(), classes);
  }

  /**
   * Gets a specific Morphia datastore instance
   *
   * @param classLoader the class loader to get.
   * @param name the name of the database
   * @param classes the classes to map.
   *
   * @return the datastore
   */
  @NotNull
  private Datastore getMorphiaDatastore(@NotNull final ClassLoader classLoader, @NotNull final String name,
                                        @NotNull final Class<?>... classes) {
    final var datastore = Morphia.createDatastore(this.getClient(), name, MapperOptions.builder()
      .classLoader(classLoader)
      .build());
    for (final var mapPackage : classes) {
      datastore.getMapper().map(mapPackage);
    }
    return datastore;
  }
}
