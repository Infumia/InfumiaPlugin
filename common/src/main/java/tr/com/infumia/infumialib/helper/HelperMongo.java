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

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

@Getter
public final class HelperMongo implements Mongo {

  @NotNull
  private final MongoClient client;

  @NotNull
  private final MongoDatabase database;

  @NotNull
  private final Morphia morphia = new Morphia();

  @NotNull
  private final Datastore morphiaDatastore;

  public HelperMongo(@NotNull final MongoDatabaseCredentials credentials) {
    final var mongoCredential = MongoCredential.createCredential(
      credentials.getUsername(),
      credentials.getDatabase(),
      credentials.getPassword().toCharArray());
    this.client = new MongoClient(
      new ServerAddress(credentials.getAddress(), credentials.getPort()),
      mongoCredential,
      MongoClientOptions.builder().build());
    this.database = this.getDatabase(credentials.getDatabase());
    this.morphiaDatastore = this.getMorphiaDatastore(credentials.getDatabase());
  }

  @Override
  public void close() {
    this.client.close();
  }

  @NotNull
  @Override
  public MongoDatabase getDatabase(@NotNull final String name) {
    return this.client.getDatabase(name);
  }

  @NotNull
  @Override
  public Datastore getMorphiaDatastore(@NotNull final String name) {
    return this.morphia.createDatastore(this.client, name);
  }
}
