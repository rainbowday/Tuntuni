/*
 * Copyright 2016 Tuntuni.
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
package org.tuntuni.video;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import javafx.scene.image.Image;
import org.tuntuni.util.Commons;

/**
 *
 */
public class ImageFrame implements Externalizable, Comparable<ImageFrame> {

    private long mTime;
    private byte[] mBuffer;

    public ImageFrame() {

    }

    public ImageFrame(long time, byte[] buffer) {
        mTime = time;
        mBuffer = buffer;
    }

    public Image getImage() {
        return Commons.bytesToImage(mBuffer);
    }

    @Override
    public void writeExternal(ObjectOutput oo) throws IOException {
        oo.writeLong(mTime);
        oo.writeInt(mBuffer.length);
        oo.write(mBuffer);
    }

    @Override
    public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
        mTime = oi.readLong();
        mBuffer = new byte[oi.readInt()];
        oi.readFully(mBuffer);
    }

    @Override
    public int compareTo(ImageFrame t) {
        return mTime == t.getTime() ? 0 : (mTime < t.getTime() ? -1 : 1);
    }

    @Override
    public boolean equals(Object t) {
        if (t == null || !(t instanceof ImageFrame)) {
            return false;
        }
        return ((ImageFrame) t).getTime() == mTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.mTime ^ (this.mTime >>> 32));
        return hash;
    }

    public long getTime() {
        return mTime;
    }

    public byte[] getBuffer() {
        return mBuffer;
    }

    public ByteBuffer getByteBuffer() {
        return ByteBuffer.wrap(mBuffer);
    }
}
