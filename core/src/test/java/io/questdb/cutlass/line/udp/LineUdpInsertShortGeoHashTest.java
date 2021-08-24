/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2020 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.cutlass.line.udp;

import io.questdb.cairo.*;
import io.questdb.cutlass.line.LineProtoSender;
import io.questdb.test.tools.TestUtils;

public class LineUdpInsertShortGeoHashTest extends LineUdpInsertGeoHashTest {
    @Override
    public void testGeoHashes() throws Exception {
        assertGeoHash(15, 3, 10,
                "geohash\ttimestamp\n" +
                        "9v1\t1970-01-01T00:00:01.000000Z\n" +
                        "46s\t1970-01-01T00:00:02.000000Z\n" +
                        "jnw\t1970-01-01T00:00:03.000000Z\n" +
                        "zfu\t1970-01-01T00:00:04.000000Z\n" +
                        "hp4\t1970-01-01T00:00:05.000000Z\n" +
                        "wh4\t1970-01-01T00:00:06.000000Z\n" +
                        "s2z\t1970-01-01T00:00:07.000000Z\n" +
                        "1cj\t1970-01-01T00:00:08.000000Z\n" +
                        "mmt\t1970-01-01T00:00:09.000000Z\n" +
                        "71f\t1970-01-01T00:00:10.000000Z\n");
    }

    @Override
    public void testGeoHashesTruncating() throws Exception {
        assertGeoHash(13, 3, 10,
                "geohash\ttimestamp\n" +
                        "0100111011000\t1970-01-01T00:00:01.000000Z\n" +
                        "0010000110110\t1970-01-01T00:00:02.000000Z\n" +
                        "1000110100111\t1970-01-01T00:00:03.000000Z\n" +
                        "1111101110110\t1970-01-01T00:00:04.000000Z\n" +
                        "1000010101001\t1970-01-01T00:00:05.000000Z\n" +
                        "1110010000001\t1970-01-01T00:00:06.000000Z\n" +
                        "1100000010111\t1970-01-01T00:00:07.000000Z\n" +
                        "0000101011100\t1970-01-01T00:00:08.000000Z\n" +
                        "1001110011110\t1970-01-01T00:00:09.000000Z\n" +
                        "0011100001011\t1970-01-01T00:00:10.000000Z\n");
    }

    @Override
    public void testTableHasGeoHashMessageDoesNot() throws Exception {
        TestUtils.assertMemoryLeak(() -> {
            try (CairoEngine engine = new CairoEngine(configuration)) {
                try (AbstractLineProtoReceiver receiver = createLineProtoReceiver(engine)) {
                    createTable(engine, 14);
                    receiver.start();
                    try (LineProtoSender sender = createLineProtoSender()) {
                        sender.metric(tableName).field("carrots", "9").$(1000000000L);
                        sender.metric(tableName).field("carrots", "4").$(2000000000L);
                        sender.metric(tableName).field("carrots", "j").$(3000000000L);
                        sender.flush();
                    }
                    assertReader("geohash\ttimestamp\tcarrots\n" +
                                    "\t1970-01-01T00:00:01.000000Z\t9\n" +
                                    "\t1970-01-01T00:00:02.000000Z\t4\n" +
                                    "\t1970-01-01T00:00:03.000000Z\tj\n",
                            "carrots");
                }
            }
        });
    }

    @Override
    public void testSeeminglyGoodLookingStringWhichIsTooLongToBeAGeoHash() throws Exception {
        TestUtils.assertMemoryLeak(() -> {
            try (CairoEngine engine = new CairoEngine(configuration)) {
                try (AbstractLineProtoReceiver receiver = createLineProtoReceiver(engine)) {
                    createTable(engine, 14);
                    receiver.start();
                    sendGeoHashLine("9v1s8hm7wpkssv1h");
                    assertReader("geohash\ttimestamp\n" +
                            "\t1970-01-01T00:00:01.000000Z\n");
                }
            }
        });
    }

    @Override
    public void testGeoHashesNotEnoughPrecision() throws Exception {
        assertGeoHash(15, 2, 10,
                "geohash\ttimestamp\n" +
                        "\t1970-01-01T00:00:01.000000Z\n" +
                        "\t1970-01-01T00:00:02.000000Z\n" +
                        "\t1970-01-01T00:00:03.000000Z\n" +
                        "\t1970-01-01T00:00:04.000000Z\n" +
                        "\t1970-01-01T00:00:05.000000Z\n" +
                        "\t1970-01-01T00:00:06.000000Z\n" +
                        "\t1970-01-01T00:00:07.000000Z\n" +
                        "\t1970-01-01T00:00:08.000000Z\n" +
                        "\t1970-01-01T00:00:09.000000Z\n" +
                        "\t1970-01-01T00:00:10.000000Z\n");
    }

    @Override
    public void testWrongCharGeoHashes() throws Exception {
        TestUtils.assertMemoryLeak(() -> {
            try (CairoEngine engine = new CairoEngine(configuration)) {
                try (AbstractLineProtoReceiver receiver = createLineProtoReceiver(engine)) {
                    createTable(engine, 9);
                    receiver.start();
                    sendGeoHashLine("sp-");
                    assertReader("geohash\ttimestamp\n" +
                            "\t1970-01-01T00:00:01.000000Z\n");
                }
            }
        });
    }

    @Override
    public void testNullGeoHash() throws Exception {
        TestUtils.assertMemoryLeak(() -> {
            try (CairoEngine engine = new CairoEngine(configuration)) {
                try (AbstractLineProtoReceiver receiver = createLineProtoReceiver(engine)) {
                    createTable(engine, 15);
                    receiver.start();
                    sendGeoHashLine("");
                    assertReader("geohash\ttimestamp\n" +
                            "\t1970-01-01T00:00:01.000000Z\n");
                }
            }
        });
    }
}
