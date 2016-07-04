/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hello;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dave Syer
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("mysql")
public class MysqlApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void init() {
        try (Socket socket = new Socket("localhost", 3306)) {
            socket.getInputStream().close();
        }
        catch (Exception e) {
            if (Boolean.getBoolean("verify.database")) {
                // User can force failure here by opting in to verify.database=true
                fail("No database available");
            }
            Assume.assumeNoException("No database available", e);
        }
    }

    @Test
    public void contextLoads() {
        List<Map<String, Object>> records = jdbcTemplate.queryForList(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
                "Josh");
        assertThat(records.size(), equalTo(2));
    }

}
