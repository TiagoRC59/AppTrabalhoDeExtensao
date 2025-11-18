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
import com.professora.aulasparticulares.data.data.entity.Agendamento
import com.professora.aulasparticulares.data.data.entity.Aluno
import com.professora.aulasparticulares.data.ui.AgendamentoViewModel
import com.professora.aulasparticulares.data.ui.AlunoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendamentosScreen(
    viewModel: AgendamentoViewModel,
    alunoViewModel: AlunoViewModel,
    onNavigateBack: () -> Unit
) {
    val agendamentos by viewModel.agendamentos.collectAsState()
    val alunos by alunoViewModel.alunos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendamentos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, "Agendar aula")
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
            items(agendamentos) { agendamento ->
                val aluno = alunos.find { it.id == agendamento.alunoId }
                AgendamentoCard(
                    agendamento = agendamento,
                    nomeAluno = aluno?.nome ?: "Aluno não encontrado",
                    onToggleRealizada = {
                        viewModel.marcarComoRealizada(agendamento.id, !agendamento.realizada)
                    },
                    onDelete = { viewModel.deletarAgendamento(agendamento) }
                )
            }
        }
    }

    if (showDialog) {
        AgendamentoDialog(
            alunos = alunos,
            onDismiss = { showDialog = false },
            onConfirm = { agendamento ->
                viewModel.inserirAgendamento(agendamento)
                showDialog = false
            }
        )
    }
}

@Composable
fun AgendamentoCard(
    agendamento: Agendamento,
    nomeAluno: String,
    onToggleRealizada: () -> Unit,
    onDelete: () -> Unit
) {
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
                Text("${agendamento.materia} - ${agendamento.duracao} min",
                    style = MaterialTheme.typography.bodyMedium)
                Text("${agendamento.data} às ${agendamento.horario}",
                    style = MaterialTheme.typography.bodySmall)
                if (agendamento.realizada) {
                    Text("✓ Realizada", color = MaterialTheme.colorScheme.primary)
                }
            }
            Row {
                IconButton(onClick = onToggleRealizada) {
                    Icon(
                        if (agendamento.realizada) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        "Marcar como realizada"
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Excluir")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendamentoDialog(
    alunos: List<Aluno>,
    onDismiss: () -> Unit,
    onConfirm: (Agendamento) -> Unit
) {
    var alunoSelecionado by remember { mutableStateOf<Aluno?>(null) }
    var data by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var duracao by remember { mutableStateOf("60") }
    var materia by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }
    var expandedAluno by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agendar Aula") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                    value = data,
                    onValueChange = { data = it },
                    label = { Text("Data (dd/mm/aaaa)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = horario,
                    onValueChange = { horario = it },
                    label = { Text("Horário (HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = duracao,
                    onValueChange = { duracao = it },
                    label = { Text("Duração (minutos)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = materia,
                    onValueChange = { materia = it },
                    label = { Text("Matéria") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = observacoes,
                    onValueChange = { observacoes = it },
                    label = { Text("Observações") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    alunoSelecionado?.let { aluno ->
                        val agendamento = Agendamento(
                            alunoId = aluno.id,
                            data = data,
                            horario = horario,
                            duracao = duracao.toIntOrNull() ?: 60,
                            materia = materia,
                            observacoes = observacoes,
                            realizada = false
                        )
                        onConfirm(agendamento)
                    }
                },
                enabled = alunoSelecionado != null && data.isNotBlank() &&
                        horario.isNotBlank() && materia.isNotBlank()
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
