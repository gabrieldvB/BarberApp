package com.suaequipe.barberapp.ui.agendamentos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter; // Importar ArrayAdapter
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner; // Importar Spinner
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.suaequipe.barberapp.Cliente; // Importar Cliente
import com.suaequipe.barberapp.ClienteRepository; // Importar ClienteRepository
import com.suaequipe.barberapp.databinding.FragmentAgendamentosBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors; // Para Java 8+ streams, se usado
import android.util.Log;
import com.suaequipe.barberapp.Agendamento;
import com.suaequipe.barberapp.AgendamentoRepository;

public class AgendamentosFragment extends Fragment {

    private FragmentAgendamentosBinding binding;
    private Calendar calendario = Calendar.getInstance();

    private int anoSelecionado, mesSelecionado, diaSelecionado;
    private int horaSelecionada, minutoSelecionado;
    private boolean dataSelecionada = false;
    private boolean horaSelecionadaFlag = false;

    private List<Cliente> listaDeClientesCadastrados; // Para popular o Spinner
    private ArrayAdapter<String> spinnerAdapterClientes;
    private List<String> nomesClientesParaSpinner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAgendamentosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClienteSpinner(); // Configurar o Spinner de clientes

        binding.btnSelecionarData.setOnClickListener(v -> mostrarDatePickerDialog());
        binding.btnSelecionarHora.setOnClickListener(v -> mostrarTimePickerDialog());
        binding.btnConfirmarAgendamento.setOnClickListener(v -> confirmarAgendamento());

        binding.radioGroupTipoPagamentoAgendamento.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.radioCartaoCreditoAgendamento.getId()) {
                binding.layoutDetalhesCartaoAgendamento.setVisibility(View.VISIBLE);
            } else {
                binding.layoutDetalhesCartaoAgendamento.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Atualizar a lista de clientes no spinner sempre que o fragmento ficar visível
        // Isso garante que novos clientes cadastrados apareçam na lista.
        carregarClientesNoSpinner();
    }

    private void setupClienteSpinner() {
        nomesClientesParaSpinner = new ArrayList<>();
        // Usaremos um layout simples do Android para os itens do spinner
        spinnerAdapterClientes = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nomesClientesParaSpinner);
        spinnerAdapterClientes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerClientesAgendamento.setAdapter(spinnerAdapterClientes);
        // Carregar clientes inicialmente
        // carregarClientesNoSpinner(); // Movido para onResume para atualização
    }

    private void carregarClientesNoSpinner() {
        listaDeClientesCadastrados = ClienteRepository.getListaClientes();
        nomesClientesParaSpinner.clear(); // Limpar a lista anterior

        if (listaDeClientesCadastrados.isEmpty()) {
            nomesClientesParaSpinner.add("Nenhum cliente cadastrado");
            binding.spinnerClientesAgendamento.setEnabled(false); // Desabilitar spinner se não há clientes
        } else {
            // Para exibir no Spinner, podemos usar o nomePrincipal do cliente ou um identificador.
            // Vamos criar uma lista de Strings para o ArrayAdapter.
            for (Cliente cliente : listaDeClientesCadastrados) {
                // Você pode escolher o que exibir, por exemplo: cliente.getNomePrincipal() ou cliente.getEmail()
                String displayCliente = cliente.getNomePrincipal() + " (" + cliente.getEmail() + ")";
                nomesClientesParaSpinner.add(displayCliente);
            }
            binding.spinnerClientesAgendamento.setEnabled(true);
        }
        spinnerAdapterClientes.notifyDataSetChanged(); // Notificar o adapter que os dados mudaram
    }


    private void mostrarDatePickerDialog() {
        // (código do mostrarDatePickerDialog como estava antes)
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (viewDatePicker, year, monthOfYear, dayOfMonth) -> {
                    anoSelecionado = year;
                    mesSelecionado = monthOfYear;
                    diaSelecionado = dayOfMonth;
                    dataSelecionada = true;

                    Calendar dataCal = Calendar.getInstance();
                    dataCal.set(anoSelecionado, mesSelecionado, diaSelecionado);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    binding.textViewDataSelecionada.setText(sdf.format(dataCal.getTime()));
                }, ano, mes, dia);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void mostrarTimePickerDialog() {
        // (código do mostrarTimePickerDialog como estava antes)
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (viewTimePicker, hourOfDay, minuteOfDay) -> {
                    horaSelecionada = hourOfDay;
                    minutoSelecionado = minuteOfDay;
                    horaSelecionadaFlag = true;
                    binding.textViewHoraSelecionada.setText(String.format(Locale.getDefault(), "%02d:%02d", horaSelecionada, minutoSelecionado));
                }, hora, minuto, true);
        timePickerDialog.show();
    }

    private void confirmarAgendamento() {
        // 0. Obter o cliente selecionado (como implementado antes)
        Cliente clienteSelecionadoParaAgendamento = null;
        String nomeClienteParaAgendamento = "N/A";
        String emailClienteParaAgendamento = "N/A";

        if (listaDeClientesCadastrados != null && !listaDeClientesCadastrados.isEmpty()) {
            int clientePosition = binding.spinnerClientesAgendamento.getSelectedItemPosition();
            if (binding.spinnerClientesAgendamento.isEnabled() && clientePosition >= 0 && clientePosition < listaDeClientesCadastrados.size()) {
                clienteSelecionadoParaAgendamento = listaDeClientesCadastrados.get(clientePosition);
                nomeClienteParaAgendamento = clienteSelecionadoParaAgendamento.getNomePrincipal();
                emailClienteParaAgendamento = clienteSelecionadoParaAgendamento.getEmail();
            } else if (!binding.spinnerClientesAgendamento.isEnabled()){
                Toast.makeText(getContext(), "Nenhum cliente cadastrado para selecionar.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(getContext(), "Por favor, selecione um cliente.", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(getContext(), "Nenhum cliente cadastrado para selecionar.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Obter o serviço selecionado (como antes)
        int selectedServicoId = binding.radioGroupServicos.getCheckedRadioButtonId();
        String servicoSelecionado = "";
        if (selectedServicoId == -1) { /* ... validação ... */ return; }
        RadioButton rbServico = binding.getRoot().findViewById(selectedServicoId);
        servicoSelecionado = rbServico.getText().toString();

        // 2. Verificar se data e hora foram selecionadas (como antes)
        if (!dataSelecionada) { /* ... validação ... */ return; }
        if (!horaSelecionadaFlag) { /* ... validação ... */ return; }

        // 3. Obter a forma de pagamento (como antes)
        int selectedTipoPagamentoId = binding.radioGroupTipoPagamentoAgendamento.getCheckedRadioButtonId();
        String tipoPagamento = "";
        if (selectedTipoPagamentoId == -1) { /* ... validação ... */ return; }
        RadioButton rbTipoPagamento = binding.getRoot().findViewById(selectedTipoPagamentoId);
        tipoPagamento = rbTipoPagamento.getText().toString();

        // 4. Coletar dados do cartão se for o tipo de pagamento selecionado
        String detalhesCartaoParaSalvar = null; // String para armazenar um resumo
        if (tipoPagamento.equals("Cartão de Crédito")) {
            String bandeiraCartao = "";
            int selectedBandeiraId = binding.radioGroupBandeiraCartaoAgendamento.getCheckedRadioButtonId();
            if (selectedBandeiraId != -1) {
                RadioButton rbBandeira = binding.getRoot().findViewById(selectedBandeiraId);
                bandeiraCartao = rbBandeira.getText().toString();
            }
            String numeroCartao = binding.editTextNumeroCartaoAgendamento.getText().toString().trim();
            String nomeTitular = binding.editTextNomeTitularAgendamento.getText().toString().trim();
            String dataValidade = binding.editTextDataValidadeAgendamento.getText().toString().trim(); // Adicionado .trim()
            String cvv = binding.editTextCVVAgendamento.getText().toString().trim(); // Adicionado .trim()

            if (bandeiraCartao.isEmpty() || numeroCartao.isEmpty() || nomeTitular.isEmpty() ||
                    dataValidade.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(getContext(), "Preencha todos os dados do cartão!", Toast.LENGTH_SHORT).show();
                return;
            }
            // Para o repositório, podemos salvar apenas a bandeira e os últimos 4 dígitos, por exemplo
            // Por simplicidade, vamos apenas salvar a bandeira por enquanto como "detalhes do cartão"
            detalhesCartaoParaSalvar = bandeiraCartao;
        }

        Calendar dataHoraAgendamento = Calendar.getInstance();
        dataHoraAgendamento.set(anoSelecionado, mesSelecionado, diaSelecionado, horaSelecionada, minutoSelecionado);
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String dataAgendamentoStr = sdfDate.format(dataHoraAgendamento.getTime());
        String horaAgendamentoStr = sdfTime.format(dataHoraAgendamento.getTime());

        if (clienteSelecionadoParaAgendamento != null) { // Certifique-se que temos um cliente
            final int PONTOS_POR_AGENDAMENTO = 10; // Defina quantos pontos dar
            clienteSelecionadoParaAgendamento.adicionarPontos(PONTOS_POR_AGENDAMENTO);
            Log.d("AgendamentosFragment", "Adicionado " + PONTOS_POR_AGENDAMENTO + " pontos para " + clienteSelecionadoParaAgendamento.getNomePrincipal() + ". Total: " + clienteSelecionadoParaAgendamento.getPontosMilhas());
            // IMPORTANTE: Esta alteração no objeto clienteSelecionadoParaAgendamento
            // refletirá na lista do ClienteRepository porque a lista contém referências
            // aos mesmos objetos Cliente.
        }

        // Criar e Salvar Agendamento (como já estava)
        Agendamento novoAgendamento = new Agendamento(
                nomeClienteParaAgendamento,
                emailClienteParaAgendamento,
                servicoSelecionado,
                dataAgendamentoStr,
                horaAgendamentoStr,
                tipoPagamento,
                detalhesCartaoParaSalvar
        );
        AgendamentoRepository.addAgendamento(novoAgendamento);
        Log.d("AgendamentosFragment", "Novo agendamento salvo. Total: " + AgendamentoRepository.getListaAgendamentos().size());
        // 5. Exibir confirmação (Toast)
        String clienteInfoParaToast = "Cliente: " + nomeClienteParaAgendamento;
        String infoCartaoParaToast = (detalhesCartaoParaSalvar != null && !detalhesCartaoParaSalvar.isEmpty()) ? "\nCartão: " + detalhesCartaoParaSalvar : "";

        String mensagem = "Agendamento confirmado!\n" + clienteInfoParaToast +
                "\nServiço: " + servicoSelecionado +
                "\nData: " + dataAgendamentoStr +
                "\nHora: " + horaAgendamentoStr +
                "\nPagamento: " + tipoPagamento + infoCartaoParaToast;
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_LONG).show();

        // Limpar campos (como estava antes)
        binding.spinnerClientesAgendamento.setSelection(0);
        binding.radioGroupServicos.clearCheck();
        binding.textViewDataSelecionada.setText("Nenhuma data selecionada");
        binding.textViewHoraSelecionada.setText("Nenhuma hora selecionada");
        dataSelecionada = false;
        horaSelecionadaFlag = false;
        binding.radioGroupTipoPagamentoAgendamento.clearCheck();
        binding.layoutDetalhesCartaoAgendamento.setVisibility(View.GONE);
        if (binding.layoutDetalhesCartaoAgendamento.getVisibility() == View.VISIBLE) {
            binding.radioGroupBandeiraCartaoAgendamento.clearCheck();
            // ... (limpar campos do cartão)
            binding.editTextNumeroCartaoAgendamento.setText("");
            binding.editTextNomeTitularAgendamento.setText("");
            binding.editTextDataValidadeAgendamento.setText("");
            binding.editTextCVVAgendamento.setText("");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}