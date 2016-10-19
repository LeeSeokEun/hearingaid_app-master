package com.hari.hearingaid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import ca.uol.aig.fftpack.RealDoubleFFT;

public class Classify extends Activity {

	int frequency = 16000;
	@SuppressWarnings("deprecation")
	int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	private RealDoubleFFT transformer;
	int blockSize = 256;
	boolean police = false, fire = false, carhorn = false;
	public AudioRecord audioRecord;
	boolean started = false;
	RecordAudio recordTask;
	ImageView imageView;
	Bitmap bitmap;
	Canvas canvas;
	Paint paint;
	int count1 = 0, count2 = 0, count3 = 0; // Counts for classifying sounds
	// Count1 for Police Siren
	// Count2 for Car Horn
	// COunt3 for Fire Alarm
	int dummy = 0;
	int max;
	int mcompare=0;
	int test1=0;
	public AudioReader audioReader;
	private int sampleDecimate = 1;
	public static int temp;



	// AudioRecord audioRecord;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classify);

		started = true;
		recordTask = new RecordAudio();
		recordTask.execute();

		transformer = new RealDoubleFFT(blockSize);

		imageView = (ImageView) this.findViewById(R.id.ImageView01);
		bitmap = Bitmap.createBitmap(512, 200,
				Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);
		paint = new Paint();
		paint.setColor(Color.GREEN);
		imageView.setImageBitmap(bitmap);

		audioReader = new AudioReader();

      audioReader.startReader(frequency, blockSize * sampleDecimate, new AudioReader.Listener()
      {
         //Log.d("check");
         @Override
         public final void onReadComplete(int dB)
         {
            temp = dB+88;

            Log.e("###temp", temp +"dB");
         }
         @Override
         public void onReadError(int error)
         {
         }
      });



	}

	/* public double calculateRMS(short[] sndChunk) {

		// Init some vars
		double rms = 0;
		double sum = 0;

		// Sum the values in the buffer
		for(int i = 0; i < sndChunk.length; i++) {
			sum += Math.pow(sndChunk[i], 2);
		}

		// Get the mean and take the square root to get rms
		rms = (double) Math.sqrt(sum/sndChunk.length);

		return rms;

	}
	public double calculateDb(double rms) {

		// Init some vars
		double db = 0;
		double ref = 32767.0; // reference value used for dB calculation

		// dB calculation
		db = 20 * Math.log10(rms/ref);

		return db;

	}




*/
	public void onStop() {
		super.onStop();
		started = false;
		recordTask.cancel(true);
		audioRecord.release();
	}

	public class RecordAudio extends AsyncTask<Void, double[], Void> {
		TextView tv  = (TextView) findViewById(R.id.tv1);
		TextView tv1  = (TextView) findViewById(R.id.tv2);




		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				int bufferSize = AudioRecord.getMinBufferSize(frequency,
						channelConfiguration, audioEncoding);

				audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
						frequency, channelConfiguration, audioEncoding,
						bufferSize);

				short[] buffer = new short[blockSize];
				double[] toTransform = new double[blockSize];

				audioRecord.startRecording();

				//double rms = 0;
				//double db = 0;

				while (started) {
					int bufferReadResult = audioRecord.read(buffer, 0,
							blockSize);

					for (int i = 0; i < blockSize && i < bufferReadResult; i++) {
						toTransform[i] = (double) buffer[i] / 32768.0;

					}

					audioRecord.read(buffer, 0, blockSize);

					// Calculate the RMS of the audio chunk
					//rms = calculateRMS(buffer);

					// Calculate dB
					//db = calculateDb(rms);

					// Update the UI with the rms value
					//publishProgress(db);

					transformer.ft(toTransform);

					publishProgress(toTransform);

				}

				audioRecord.stop();

			} catch (Throwable t) {
				t.printStackTrace();
				Log.e("AudioRecord", "Recording Failed");
			}
			return null;


		}






				@Override
		protected void onProgressUpdate( double[]... toTransform) {

			canvas.drawColor(Color.BLACK);

			for (int i = 0; i < toTransform[0].length; i++) {
				int x = i;
				if (toTransform[0][i] > 30) {
					dummy = i;

				}
				int downy = (int) (200 - (toTransform[0][i] * 10));
				int upy = 200;

				test1 = temp;
				if (test1 > mcompare)
					mcompare = test1;
				if (test1 < 0)
					mcompare = 0;

				//canvas.drawLine(x, downy, x, upy, paint);


			}

			//db_val = toTransform[0][dummy];
			//String output = String.format("dB", temp);
			//tv.setText(temp);
			//tv.setText("주파수 : " +(double)(mcompare) + " Hz" + "    Magnitude : " +((double) (dummy)) / 512 * 8000 );
					tv.setText("Decibel : "+ temp );
					tv1.setText("Max Decibel : "+ mcompare );
			//Log.e("########### #####데시벨:", db_val*100  +"dB");


			//Log.e("주파수값:", test1+"hz");
			//Log.e("dumy:", ((double) (dummy)) / 512 * 8000 +"hz");
			//Log.e("########### #####데시벨:", temp +85 +"dB");

			//imageView.invalidate();
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}