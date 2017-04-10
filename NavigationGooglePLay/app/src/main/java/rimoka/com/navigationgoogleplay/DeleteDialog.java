package rimoka.com.navigationgoogleplay;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Created by singun on 3/23/2017.
 */

public class DeleteDialog extends DialogFragment {
    public static DeleteDialog newInstance(int position){
        //muốn đưa đối số vào DialogFragment
        DeleteDialog dfrag=new DeleteDialog();
        Bundle args = new Bundle();
        args.putInt("position", position);
        dfrag.setArguments(args);
        return dfrag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Are you sure to delete!")
                .setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Toast.makeText(getContext(),Integer.toString(pos),Toast.LENGTH_SHORT).show();
                                int pos = getArguments().getInt("position");
                                try {
                                    ItemTwoFragment tb4 = new ItemTwoFragment();
                                    int b=tb4.getVitri();
                                    if (pos ==b) {
                                        Toast.makeText(getContext(),"không được xóa!",Toast.LENGTH_SHORT).show();
                                    }
                                   //k cho xóa nếu bài đó đang dùng(đang phát hoặc tạm dừng)
                                    else tb4.delListSong(pos);
                                }catch (Exception e)
                                {}
                                dismiss();
                            }
                        }
                )
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                               dismiss();
                            }
                        }
                )
                .create();
    }
//    public int getRawpResIdByName(String resName)
//
//    {
//        String pkgName = getContext().getPackageName();
//        int resID = getContext().getResources().getIdentifier(resName, "raw", pkgName);
//        Log.i("CustomListView", "Res Name: " + resName + "==> Res ID = " + resID);
//        return resID;
//    }
}
