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
import com.professora.aulasparticulares.data.ui.AlunoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlunosScreen(
    viewModel: AlunoViewModel,
    onNavigateBack: () -> Unit
) {
    val alunos by viewModel.alunos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var alunoEditando by remember { mutableStateOf<Aluno?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alunos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                alunoEditando = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, "Adicionar aluno")
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
            items(alunos) { aluno ->
                AlunoCard(
                    aluno = aluno,
                    onEdit = {
                        alunoEditando = aluno
                        showDialog = true
                    },
                    onDelete = { viewModel.deletarAluno(aluno) }
                )
            }
        }
    }

    if (showDialog) {
        AlunoDialog(
            aluno = alunoEditando,
            onDismiss = { showDialog = false },
            onConfirm = { aluno ->
                if (alunoEditando == null) {
                    viewModel.inserirAluno(aluno)
                } else {
                    viewModel.atualizarAluno(aluno)
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun AlunoCard(
    aluno: Aluno,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(aluno.nome, style = MaterialTheme.typography.titleMedium)
                Text(aluno.telefone, style = MaterialTheme.typography.bodyMedium)
                Text(aluno.email, style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Excluir")
                }
            }
        }
    }
}

@Composable
fun AlunoDialog(
    aluno: Aluno?,
    onDismiss: () -> Unit,
    onConfirm: (Aluno) -> Unit
) {
    var nome by remember { mutableStateOf(aluno?.nome ?: "") }
    var telefone by remember { mutableStateOf(aluno?.telefone ?: "") }
    var email by remember { mutableStateOf(aluno?.email ?: "") }
    var observacoes by remember { mutableStateOf(aluno?.observacoes ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (aluno == null) "Novo Aluno" else "Editar Aluno") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = telefone,
                    onValueChange = { telefone = it },
                    label = { Text("Telefone") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = observacoes,
                    onValueChange = { observacoes = it },
                    label = { Text("Observações") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val novoAluno = Aluno(
                        id = aluno?.id ?: 0,
                        nome = nome,
                        telefone = telefone,
                        email = email,
                        observacoes = observacoes
                    )
                    onConfirm(novoAluno)
                },
                enabled = nome.isNotBlank() && telefone.isNotBlank()
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
