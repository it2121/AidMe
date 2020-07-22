import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.fragment.app.DialogFragment;

public  class message extends DialogFragment
{
   public void show(String title, String message)
    {
        dialog = new AlertDialog.Builder(getActivity()) // Pass a reference to your main activity here
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private AlertDialog dialog;
}