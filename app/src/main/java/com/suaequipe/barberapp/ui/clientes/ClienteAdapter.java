package com.suaequipe.barberapp.ui.clientes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton; // NOVO
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.suaequipe.barberapp.Cliente;
import com.suaequipe.barberapp.R;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    private List<Cliente> listaClientes;
    private final OnDeleteClickListener listener; // NOVO: Listener

    // NOVA INTERFACE
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    // CONSTRUTOR MODIFICADO
    public ClienteAdapter(List<Cliente> listaClientes, OnDeleteClickListener listener) {
        this.listaClientes = listaClientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente clienteAtual = listaClientes.get(position);
        holder.textViewNome.setText(clienteAtual.getNomePrincipal());
        holder.textViewCelular.setText(clienteAtual.getCelular());
        holder.textViewEmail.setText(clienteAtual.getEmail());

        // NOVO: Ação para o botão de excluir
        holder.btnExcluir.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaClientes == null ? 0 : listaClientes.size();
    }

    // VIEWHOLDER MODIFICADO
    static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome;
        TextView textViewCelular;
        TextView textViewEmail;
        ImageButton btnExcluir; // NOVO

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewItemClienteNome);
            textViewCelular = itemView.findViewById(R.id.textViewItemClienteCelular);
            textViewEmail = itemView.findViewById(R.id.textViewItemClienteEmail);
            btnExcluir = itemView.findViewById(R.id.btnExcluirCliente); // NOVO
        }
    }

    public void atualizarLista(List<Cliente> novaLista) {
        this.listaClientes = novaLista;
        notifyDataSetChanged();
    }
}