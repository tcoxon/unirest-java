package com.mashape.unirest.http.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.entity.mime.content.FileBody;

public class ProgressFileBody extends FileBody implements WriteListener {

	private OutputStreamProgress outstream;
	private ProgressListener listener;
	
	public ProgressFileBody(File file, ProgressListener listener) {
		super(file);
		this.listener = listener;
	}

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		this.outstream = new OutputStreamProgress(outstream, this);
		super.writeTo(this.outstream);
	}
	
	public double getProgress() {
		long contentLength = getContentLength();
		if (outstream == null || contentLength <= 0) {
			return 0.0;
		}
		long written = outstream.getWrittenLength();
		return ((double)written)/contentLength;
	}
	
	public void written(long nBytes) {
		if (listener == null) return;
		long length = getContentLength();
		if (length == 0) {
			listener.progress(getProgress(), 0.0f);
		}
		double change = ((double)nBytes)/length;
		listener.progress(getProgress(), change);
	}

}
