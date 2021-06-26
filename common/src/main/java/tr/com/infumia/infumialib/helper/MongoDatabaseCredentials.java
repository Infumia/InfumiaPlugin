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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the credentials for a remote database.
 */
@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(cacheStrategy = EqualsAndHashCode.CacheStrategy.LAZY)
public final class MongoDatabaseCredentials {

  @NotNull
  private final String authSource;

  @NotNull
  private final String database;

  @NotNull
  private final String host;

  @NotNull
  private final String password;

  private final int port;

  @NotNull
  private final String uri;

  @NotNull
  private final String username;

  public static MongoDatabaseCredentials of(@NotNull final String authSource, @NotNull final String database,
                                            @NotNull final String host, @NotNull final String password,
                                            final int port, @NotNull final String uri, @NotNull final String username) {
    return new MongoDatabaseCredentials(authSource, database, host, password, port, uri, username);
  }

  @NotNull
  public static MongoDatabaseCredentials of(@NotNull final String host, @NotNull final String database) {
    return MongoDatabaseCredentials.of("admin", database, host, "", 27017, "", "");
  }
}
