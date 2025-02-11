/*
 * Copyright (c) 2003-2005, Henri Yandell
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the 
 * following conditions are met:
 * 
 * + Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimer.
 * 
 * + Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimer in the documentation 
 *   and/or other materials provided with the distribution.
 * 
 * + Neither the name of Simple-JNDI nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.osjava.sj;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

public class EncTest {

    private InitialContext initialContext;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() throws NamingException {
        initialContext.close();
        initialContext = null;
        System.clearProperty("java.naming.factory.initial");
        System.clearProperty("org.osjava.sj.root");
        System.clearProperty("org.osjava.sj.delimiter");
        System.clearProperty("org.osjava.sj.space");
        System.clearProperty("org.osjava.sj.jndi.shared");
    }

    @Test
    public void testSystemPropertyContext() throws NamingException {

        System.setProperty("java.naming.factory.initial", "org.osjava.sj.SimpleContextFactory");
        System.setProperty("org.osjava.sj.root", "src/test/resources/roots/enc-test");
        System.setProperty("org.osjava.sj.delimiter", "/");
        System.setProperty("org.osjava.sj.space", "java:comp/env");
        try {
            initialContext = new InitialContext();
        } catch(NamingException ne) {
            ne.printStackTrace();
        }

        String dsString = "SJDataSource{url='foofoo', poolname='pool', useSharding=false}";
        Context envContext = (Context) initialContext.lookup("java:comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
        assertEquals( dsString, ds.toString() );
    }

}
