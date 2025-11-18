package com.professora.aulasparticulares.data.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.professora.aulasparticulares.data.data.entity.Aluno
import com.professora.aulasparticulares.data.data.entity.Pagamento
import com.professora.aulasparticulares.data.ui.AlunoViewModel
import com.professora.aulasparticulares.data.ui.PagamentoViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagamentosScreen(
    viewModel: PagamentoViewModel,
    alunoViewModel: AlunoViewModel,
    onNavigateBack: () -> Unit
) {
    val pagamentos by viewModel.pagamentos.collectAsState()
    val alunos by alunoViewModel.alunos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pagamentos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, "Adicionar pagamento")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pagamentos) { pagamento ->
                val aluno = alunos.find { it.id == pagamento.alunoId }
                PagamentoCard(
                    pagamento = pagamento,
                    nomeAluno = aluno?.nome ?: "Aluno não encontrado",
                    onDelete = { viewModel.deletarPagamento(pagamento) }
                )
            }
        }
    }

    if (showDialog) {
        PagamentoDialog(
            alunos = alunos,
            onDismiss = { showDialog = false },
            onConfirm = { pagamento ->
                viewModel.inserirPagamento(pagamento)
                showDialog = false
            }
        )
    }
}

@Composable
fun PagamentoCard(
    pagamento: Pagamento,
    nomeAluno: String,
    onDelete: () -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(nomeAluno, style = MaterialTheme.typography.titleMedium)
                Text(formatter.format(pagamento.valor), style = MaterialTheme.typography.bodyLarge)
                Text(
                    "${pagamento.dataPagamento} - ${pagamento.metodoPagamento}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Excluir")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagamentoDialog(
    alunos: List<Aluno>,
    onDismiss: () -> Unit,
    onConfirm: (Pagamento) -> Unit
) {
    var alunoSelecionado by remember { mutableStateOf<Aluno?>(null) }
    var valor by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var metodo by remember { mutableStateOf("Dinheiro") }
    var expandedAluno by remember { mutableStateOf(false) }
    var expandedMetodo by remember { mutableStateOf(false) }
    val metodosPagamento = listOf("Dinheiro", "PIX", "Cartão", "Transferência")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar Pagamento") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Dropdown de Alunos
                ExposedDropdownMenuBox(
                    expanded = expandedAluno,
                    onExpandedChange = { expandedAluno = !expandedAluno }
                ) {
                    OutlinedTextField(
                        value = alunoSelecionado?.nome ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Aluno") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAluno) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAluno,
                        onDismissRequest = { expandedAluno = false }
                    ) {
                        alunos.forEach { aluno ->
                            DropdownMenuItem(
                                text = { Text(aluno.nome) },
                                onClick = {
                                    alunoSelecionado = aluno
                                    expandedAluno = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = valor,
                    onValueChange = { valor = it },
                    label = { Text("Valor (R$)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = data,
                    onValueChange = { data = it },
                    label = { Text("Data (dd/mm/aaaa)") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Dropdown de Método de Pagamento
                ExposedDropdownMenuBox(
                    expanded = expandedMetodo,
                    onExpandedChange = { expandedMetodo = !expandedMetodo }
                ) {
                    OutlinedTextField(
                        value = metodo,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Método de Pagamento") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMetodo) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedMetodo,
                        onDismissRequest = { expandedMetodo = false }
                    ) {
                        metodosPagamento.forEach { met ->
                            DropdownMenuItem(
                                text = { Text(met) },
                                onClick = {
                                    metodo = met
                                    expandedMetodo = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    alunoSelecionado?.let { aluno ->
                        val pagamento = Pagamento(
                            alunoId = aluno.id,
                            valor = valor.toDoubleOrNull() ?: 0.0,
                            dataPagamento = data,
                            metodoPagamento = metodo,
                            id = 0
                        )
                        onConfirm(pagamento)
                    }
                },
                enabled = alunoSelecionado != null && valor.isNotBlank() && data.isNotBlank()
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
