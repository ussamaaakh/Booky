package com.example.booky;

import static com.example.booky.Constants.MAX_BYTES_PDF;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booky.databinding.RowPdfAdminBinding;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterPdfAdmin extends RecyclerView.Adapter<AdapterPdfAdmin.HolderPdfAdmin> {

    private Context context;
    private ArrayList<ModelPdf> pdfArrayList;
    private static final String TAG="PDF_ADAPTER_TAG";

    public AdapterPdfAdmin(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
    }

    @NonNull
    @Override
    public HolderPdfAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_pdf_admin, parent, false);
        return new HolderPdfAdmin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPdfAdmin holder, int position) {
    ModelPdf model=pdfArrayList.get(position);
    String pdfId= model.getId();
    String categoryId= model.getCategoryId();
    String title=model.getTitle();
    String description= model.getDescription();
    String pdfUrl= model.getUrl();
    long timestamp= model.getTimestamp();

    String formattedDate=MyApplication.formatTimestamp(timestamp);
    holder.titleTv.setText(title);
    holder.descriptionTv.setText(description);
    holder.dateTv.setText(formattedDate);

    MyApplication.loadCategory(
            ""+categoryId,
            holder.categoryTv
    );
    MyApplication.loadPdfFormUrlSinglePage(
            ""+pdfUrl,
            ""+title,
            holder.pdfView,
            holder.progressBar
    );
    MyApplication.loadPdfSize(
            ""+pdfUrl,
            ""+title,
            holder.sizeTv
    );

   holder.itemView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Intent intent=new Intent(context,PdfDetailActivity.class);
           intent.putExtra("bookId",pdfId);
           context.startActivity(intent);
       }
   });
    }







    @Override
    public int getItemCount() {
        return pdfArrayList.size();
    }

    class HolderPdfAdmin extends RecyclerView.ViewHolder {
        PDFView pdfView;
        ProgressBar progressBar;
        TextView titleTv,descriptionTv,categoryTv,sizeTv,dateTv;
        ImageButton moreBtn;

        public HolderPdfAdmin(@NonNull View itemView) {
            super(itemView);
            pdfView = itemView.findViewById(R.id.pdfView);
            progressBar = itemView.findViewById(R.id.progressBar);
            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            categoryTv = itemView.findViewById(R.id.categoryTv);
            sizeTv = itemView.findViewById(R.id.sizeTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);




        }
    }
}
