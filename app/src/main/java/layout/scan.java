package layout;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.example.root.meeransunday.R;
import com.google.zxing.Result;
import github.nisrulz.qreader.QREader;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class scan extends Fragment implements ZXingScannerView.ResultHandler,Runnable{

    @Override
    public void run()

    {
    }

    private ZXingScannerView zXingScannerView;

    private String m_Text="";
    private  String number="";

    private OnFragmentInteractionListener mListener;

    public scan()
    {

    }
    @Override
    public void handleResult(Result rawResult) {

        onPause();
        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("My Deatils");
        builder.setMessage(rawResult.getText());
        number = rawResult.getText().substring(rawResult.getText().length() - 13);

        //
        final EditText input = new EditText(this.getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

                try {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, m_Text+" Transferred To Your Account From MTM Account", null, null);
onResume();
                }
                catch (Exception e) {

                    e.printStackTrace();

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                onResume();
            }
        });

        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        //
        AlertDialog alert1 = builder.create();
        alert1.show();


        // If you would like to resume scanning, call this method below:
        zXingScannerView.resumeCameraPreview(this);
    }

    // TODO: Rename and change types and number of parameters
    public static scan newInstance() {
        scan fragment = new scan();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this.getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);


        FrameLayout preview =(FrameLayout) view.findViewById(R.id.camera_preview);
        preview.addView(zXingScannerView);
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
        if (zXingScannerView != null) {
            zXingScannerView.stopCamera();
            zXingScannerView.stopCameraPreview();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (checkCameraHardware(getActivity().getApplicationContext())) {
            Toast.makeText(this.getActivity(), "camera hardware is free", Toast.LENGTH_SHORT).show();
            zXingScannerView.setResultHandler(this); // Register ourselves as a
            zXingScannerView.startCamera(0); // Start camera on resume
        }
    }
    public boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){

            return true;
        } else {

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
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name\

        void onFragmentInteraction(Uri uri);
    }
}
