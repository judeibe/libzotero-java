/*
 * Copyright (c) Avram Lyon, 2014.
 *
 * This file is part of libzotero-java.
 *
 * libzotero-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * libzotero-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with libzotero-java.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gimranov.libzotero;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.lang.reflect.Type;

public class ZoteroConverter implements Converter {

    private AtomConverter atomConverter;
    private JsonConverter jsonConverter;
    private XmlConverter xmlConverter;

    public ZoteroConverter() {
        Gson gson = new GsonBuilder().create();

        atomConverter = new AtomConverter(gson);
        jsonConverter = new JsonConverter(gson);
        xmlConverter = new XmlConverter();
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        String mimeType = body.mimeType();

        if (AtomConverter.MIME_TYPE.equalsIgnoreCase(mimeType)) {
            return atomConverter.fromBody(body, type);
        } else if (JsonConverter.MIME_TYPE.equalsIgnoreCase(mimeType)) {
            return jsonConverter.fromBody(body, type);
        } else if (XmlConverter.MIME_TYPE.equalsIgnoreCase(mimeType)) {
            return xmlConverter.fromBody(body, type);
        }

        throw new ConversionException("Unsupported MIME type: " + mimeType);
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
