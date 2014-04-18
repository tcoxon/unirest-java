package com.mashape.unirest.http.utils;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamProgress extends OutputStream {

	private final OutputStream out;
	private volatile long written = 0;
	private WriteListener listener;
	
	public OutputStreamProgress(OutputStream out, WriteListener listener) {
		this.out = out;
		this.listener = listener;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
		written ++;
		if (listener != null) {
			listener.written(1);
		}
	}

	@Override
	public void write(byte[] b) throws IOException {
		out.write(b);
		written += b.length;
		if (listener != null) {
			listener.written(b.length);
		}
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
		written += len;
		if (listener != null) {
			listener.written(len);
		}
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void close() throws IOException {
		out.close();
	}
	
	public long getWrittenLength() {
		return written;
	}

}
