package com.professora.aulasparticulares.data.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.professora.aulasparticulares.data.data.database.AppDatabase
import com.professora.aulasparticulares.data.data.repository.AgendamentoRepository
import com.professora.aulasparticulares.data.data.repository.AlunoRepository
import com.professora.aulasparticulares.data.data.repository.PagamentoRepository
import com.professora.aulasparticulares.data.data.repository.UsuarioRepository
import com.professora.aulasparticulares.data.ui.AgendamentoViewModel
import com.professora.aulasparticulares.data.ui.AlunoViewModel
import com.professora.aulasparticulares.data.ui.LoginViewModel
import com.professora.aulasparticulares.data.ui.PagamentoViewModel
import com.professora.aulasparticulares.data.ui.screen.AgendamentosScreen
import com.professora.aulasparticulares.data.ui.screen.AlunosScreen
import com.professora.aulasparticulares.data.ui.screen.CadastroScreen
import com.professora.aulasparticulares.data.ui.screen.HomeScreen
import com.professora.aulasparticulares.data.ui.screen.LoginScreen
import com.professora.aulasparticulares.data.ui.screen.PagamentosScreen
import com.professora.aulasparticulares.data.ui.viewmodel.AgendamentoViewModelFactory
import com.professora.aulasparticulares.data.ui.viewmodel.AlunoViewModelFactory
import com.professora.aulasparticulares.data.ui.viewmodel.LoginViewModelFactory
import com.professora.aulasparticulares.data.ui.viewmodel.PagamentoViewModelFactory

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Cadastro : Screen("cadastro")
    object Home : Screen("home")
    object Alunos : Screen("alunos")
    object Pagamentos : Screen("pagamentos")
    object Agendamentos : Screen("agendamentos")
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    database: AppDatabase
) {
    val usuarioRepository = remember { UsuarioRepository(database.usuarioDao()) }
    val alunoRepository = remember { AlunoRepository(database.alunoDao()) }
    val pagamentoRepository = remember { PagamentoRepository(database.pagamentoDao()) }
    val agendamentoRepository = remember { AgendamentoRepository(database.agendamentoDao()) }

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(usuarioRepository)
    )
    val alunoViewModel: AlunoViewModel = viewModel(
        factory = AlunoViewModelFactory(alunoRepository)
    )
    val pagamentoViewModel: PagamentoViewModel = viewModel(
        factory = PagamentoViewModelFactory(pagamentoRepository)
    )
    val agendamentoViewModel: AgendamentoViewModel = viewModel(
        factory = AgendamentoViewModelFactory(agendamentoRepository)
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToCadastro = {
                    navController.navigate(Screen.Cadastro.route)
                }
            )
        }

        composable(Screen.Cadastro.route) {
            CadastroScreen(
                viewModel = loginViewModel,
                onCadastroSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToAlunos = { navController.navigate(Screen.Alunos.route) },
                onNavigateToPagamentos = { navController.navigate(Screen.Pagamentos.route) },
                onNavigateToAgendamentos = { navController.navigate(Screen.Agendamentos.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Alunos.route) {
            AlunosScreen(
                viewModel = alunoViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Pagamentos.route) {
            PagamentosScreen(
                viewModel = pagamentoViewModel,
                alunoViewModel = alunoViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Agendamentos.route) {
            AgendamentosScreen(
                viewModel = agendamentoViewModel,
                alunoViewModel = alunoViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
