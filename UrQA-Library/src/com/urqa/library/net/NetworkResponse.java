/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.urqa.library.net;

import java.util.Arrays;
import java.util.Map;

/**
 */
public class NetworkResponse {

    /**
     * The HTTP status code.
     */
    public final int statusCode;

    /**
     * Raw data from this response.
     */
    public final byte[] data;

    /**
     * Response headers.
     */
    public final Map<String, String> headers;

    /**
     * Creates a new network response.
     *
     * @param statusCode  the HTTP status code
     * @param data        Response body
     * @param headers     Headers returned with this response, or null for none
     */
    public NetworkResponse(int statusCode, byte[] data, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.data = data;
        this.headers = headers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkResponse that = (NetworkResponse) o;

        if (statusCode != that.statusCode) return false;
        if (!Arrays.equals(data, that.data)) return false;
        if (headers != null ? !headers.equals(that.headers) : that.headers != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusCode;
        result = 31 * result + (data != null ? Arrays.hashCode(data) : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "NetworkResponse{" +
                "statusCode=" + statusCode +
                ", data=" + Arrays.toString(data) +
                ", headers=" + headers +
                '}';
    }
}