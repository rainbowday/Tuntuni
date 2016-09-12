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
package org.tuntuni.video.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import org.tuntuni.models.Logs;
import org.tuntuni.video.VideoFormat;

/**
 *
 * @author Sudipto Chandra
 */
public class MicrophoneAudio implements AudioSource, Runnable {

    private Thread mAudioThread;
    private DataLine.Info mTargetInfo;
    private TargetDataLine mTargetLine;

    private AudioFrame mFrame;
    private volatile boolean mBufferNew;

    public MicrophoneAudio() {
    }

    @Override
    public String getName() {
        return "MicrophoneAudio";
    }

    @Override
    public void open() {
        try {
            // start target line
            mTargetInfo = new DataLine.Info(
                    TargetDataLine.class, VideoFormat.getAudioFormat());
            mTargetLine = (TargetDataLine) AudioSystem.getLine(mTargetInfo);
            mTargetLine.open(VideoFormat.getAudioFormat());
            mTargetLine.start();
            // start audio thread
            mAudioThread = new Thread(this);
            mAudioThread.setDaemon(true);
            mAudioThread.start();
        } catch (LineUnavailableException ex) {
            Logs.error(getClass(), "Failed to open audio line. ERROR: {0}", ex);
        }
    }

    @Override
    public void close() {
        try {
            mTargetLine.stop();
            mTargetLine.close();
            mAudioThread.interrupt();
        } catch (Exception ex) {
        }
    }

    @Override
    public AudioFormat getFormat() {
        return mTargetLine.getFormat();
    }

    // run audio thread task
    @Override
    public void run() {
        // available: 2 5 6 7 9 10 15 25
        int size = mTargetLine.getBufferSize() / 2;
        byte[] buffer = new byte[size];

        Logs.info(getName(), "Line opened. Buffer size = {0}\n", size);
        while (isOpen()) {
            // read audio
            int len = mTargetLine.read(buffer, 0, size);
            if (len == -1) {
                return;
            }
            // update buffer
            mFrame = new AudioFrame(buffer, len);
            mBufferNew = true;
        }
    }

    @Override
    public AudioFrame getFrame() {
        mBufferNew = false;
        return mFrame;
    }

    @Override
    public boolean isAudioNew() {
        return mBufferNew;
    }

    @Override
    public boolean isOpen() {
        return mTargetLine.isOpen();
    }
}