package layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.meeransunday.R;
import com.google.zxing.Result;

import java.io.ByteArrayOutputStream;
import java.io.File;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.support.design.R.id.text;

public class scan extends Fragment implements ZXingScannerView.ResultHandler{
private ZXingScannerView zXingScannerView;
    private SurfaceView mySurfaceView;
    private QREader qrEader;
    private Camera mCamera;
    private CameraPreview mPreview;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public scan() {
    }
    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();


        // If you would like to resume scanning, call this method below:
        zXingScannerView.resumeCameraPreview(this);
    }

    // TODO: Rename and change types and number of parameters
    public static scan newInstance(String param1, String param2) {
        scan fragment = new scan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(checkCameraHardware(getActivity().getApplicationContext())) {
            zXingScannerView = new ZXingScannerView(this.getActivity().getApplicationContext());
            zXingScannerView.setResultHandler(this);
            zXingScannerView.startCamera();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
//
//   if(checkCameraHardware(getActivity().getApplicationContext())){
//        zXingScannerView  = new ZXingScannerView(this.getActivity().getApplicationContext());
//       zXingScannerView.setResultHandler(this);
//        zXingScannerView.startCamera();

        FrameLayout preview =(FrameLayout)view.findViewById(R.id.camera_preview);
        preview.addView(zXingScannerView);



//   mCamera = Camera.open();
//            mCamera.setDisplayOrientation(90);
//            Camera.Parameters params = mCamera.getParameters();
//           params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//            mCamera.setParameters(params);
//            mPreview = new CameraPreview(this.getActivity(), mCamera);
//
//
//            FrameLayout preview =(FrameLayout)view.findViewById(R.id.camera_preview);
//            preview.addView(mPreview);
//


        // Inflate the layout for this fragment
        //**magesh**//

        // Setup SurfaceView
        // -----------------
//        mySurfaceView = (SurfaceView) this.getActivity().findViewById(R.id.camera_view);
//
//        // Init QREader
//        // ------------
//        qrEader = new QREader.Builder(this, mySurfaceView, new QRDataListener() {
//            @Override
//            public void onDetected(final String data) {
//                Log.d("QREader", "Value : " + data);
//               text.post(data);
//            }
//        }).facing(QREader.BACK_CAM)
//                .enableAutofocus(true)
//                .height(mySurfaceView.getHeight())
//                .width(mySurfaceView.getWidth())
//                .build();
        return view;
    }
@Override
public   void onPause()
{
    super.onPause();
zXingScannerView.stopCamera();
}
    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        zXingScannerView.startCamera();          // Start camera on resume
    }

    /** Check if this device has a camera */
    public boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {

        if (mCamera!= null) {
            mCamera.stopPreview();
           mCamera.release();
            mCamera = null;
        }
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
