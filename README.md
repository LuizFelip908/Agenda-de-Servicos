# 📱 App Agenda de Serviços (Barbearias e Oficinas)

Aplicativo Android Nativo desenvolvido em **Kotlin** com **Jetpack Compose** e **Room Database**, voltado para o gerenciamento completo de agendamentos de serviços, clientes e controle de horários.

> **Trabalho Prático Acadêmico**: Desenvolvimento de Aplicativo Mobile Nativo Android.

---

## 🎯 Objetivo do Aplicativo

Permitir que pequenos estabelecimentos (como barbearias, salões de beleza, oficinas mecânicas e prestadores de serviços) possam:
* Cadastrar e gerenciar seus **Clientes** (com validação de telefone/WhatsApp).
* Cadastrar e gerenciar seus **Serviços** (com duração em minutos e preço em R$).
* Criar e controlar **Agendamentos** com seleção visual de data e horário.
* **Prevenir Conflitos de Agenda**: Validação em tempo real que impede sobreposição de horários para atendimentos.
* **Notificações Locais**: Lembretes automáticos no celular antes do horário do atendimento agendado.
* **Filtros e Pesquisa**: Visualização da agenda por dia, com filtros por status (*Pendente, Confirmado, Concluído, Cancelado*) e busca em tempo real.

---

## 🔄 Equivalência Acadêmica: PWA / Web vs. Android Nativo

Este projeto atende e supera os requisitos da especificação acadêmica original de PWA utilizando tecnologias nativas do ecossistema Android:

| Requisito PWA / Web | Solução Nativa Implementada no Android |
| :--- | :--- |
| **Funcionamento Offline & Service Worker** | **Room Database (SQLite)** com persistência total *offline-first*. |
| **"Adicionar à Tela Inicial" (PWA Manifest)** | Instalação nativa via **APK** e **App Shortcuts** (atalhos rápidos pressionando o ícone do aplicativo na tela inicial). |
| **Web Push Notifications** | **Android Local Notifications** (`AlarmManager` + `NotificationManager` + `NotificationChannel`), disparando lembretes nativos no celular mesmo com o aplicativo fechado. |
| **LocalStorage / IndexedDB** | **Banco de Dados Relacional Room (SQLite)** com buscas reativas usando Kotlin `Flow`. |

---

## 🛠️ Tecnologias e Bibliotecas Utilizadas

* **Linguagem**: Kotlin 2.1
* **IDE**: Android Studio
* **Interface do Usuário (UI)**: Jetpack Compose + Material Design 3 (M3)
* **Arquitetura**: MVVM (Model-View-ViewModel) + Repository Pattern
* **Banco de Dados Local**: Room 2.8 (SQLite) + KSP (Kotlin Symbol Processing)
* **Navegação**: Navigation Compose (`androidx.navigation.compose`)
* **Notificações & Agendamento**: `AlarmManager`, `NotificationManager`, `BroadcastReceiver`
* **Concorrência**: Kotlin Coroutines e StateFlow / Flow

---

## 📁 Estrutura do Projeto

```text
app/src/main/java/com/example/agendasdeservios/
│
├── data/
│   ├── local/
│   │   ├── dao/                # ClienteDao, ServicoDao, AgendamentoDao
│   │   ├── entity/             # ClienteEntity, ServicoEntity, AgendamentoEntity
│   │   ├── relation/           # AgendamentoComDetalhes (@Relation)
│   │   └── AppDatabase.kt      # Configuração Singleton do Room Database
│   └── repository/             # Repositórios (Cliente, Servico, Agendamento)
│
├── notification/
│   ├── NotificationHelper.kt   # Criação do Canal e Notificações Locais
│   ├── AgendamentoAlarmReceiver.kt # Receiver acionado pelo AlarmManager
│   └── BootReceiver.kt        # Reativação de alarmes ao reiniciar o dispositivo
│
├── ui/
│   ├── components/             # AppTopBar, StatusChip, EmptyState, DatePickerModal
│   ├── navigation/             # AppNavigation (BottomNavigation + NavHost)
│   ├── screens/
│   │   ├── agenda/             # AgendaScreen (Filtros, Pesquisa e Cards)
│   │   ├── clientes/           # ClientesScreen (Lista e Form Modal)
│   │   ├── servicos/           # ServicosScreen (Lista e Form Modal)
│   │   └── agendamento_form/   # AgendamentoFormScreen (Seleção e Validação de Conflito)
│   ├── theme/                  # Color, Type, Theme (Material Design 3)
│   └── viewmodel/              # ClienteViewModel, ServicoViewModel, AgendaViewModel, AgendamentoFormViewModel
│
├── utils/                      # DateUtils, PhoneMaskUtils, CurrencyUtils
└── MainActivity.kt             # Entrada da aplicação e permissões
```

---

## 🚀 Como Executar o Projeto no Android Studio

### Pré-requisitos
* **Android Studio** (versão Ladybug ou superior).
* **JDK 11** ou superior configurado.
* Emulador Android ou dispositivo físico rodando **Android 7.0 (API 24)** ou superior.

### Passo a Passo

1. **Clonar o Repositório**:
   ```bash
   git clone <URL_DO_REPOSITORIO_GITHUB>
   ```
2. **Abrir no Android Studio**:
   * Abra o Android Studio, escolha **Open** e selecione a pasta do projeto.
   * Aguarde o Android Studio realizar a sincronização inicial das dependências do Gradle (**Gradle Sync**).

3. **Executar a Aplicação**:
   * Selecione um emulador (ex: *Pixel 8 - API 34*) ou conecte seu celular via Depuração USB.
   * Clique no botão **Run 'app'** (`Shift + F10` ou no ícone ▶ verde).

---

## 📦 Como Gerar o Arquivo APK para Demonstração

Para gerar o arquivo **APK** de instalação e apresentação do trabalho acadêmico:

1. No menu superior do Android Studio, clique em **Build** > **Build Bundle(s) / APK(s)** > **Build APK(s)**.
2. Aguarde a conclusão da compilação.
3. Clique na notificação que aparecerá no canto inferior direito (**locate**) ou acesse o arquivo diretamente no caminho:
   ```text
   app/build/outputs/apk/debug/app-debug.apk
   ```
4. Transfira o arquivo `app-debug.apk` para o celular Android para instalar e testar a aplicação de forma nativa!

---

## 📌 Histórico de Commits do Projeto

O desenvolvimento foi dividido modularmente em 4 etapas conforme as diretrizes do trabalho:

1. `Interface e design system base com Jetpack Compose e navegação`
2. `Cadastro de clientes e serviços com banco de dados Room`
3. `Modulo de agendamentos, calendario e verificacao de conflitos de horario`
4. `Notificacoes locais, atalhos do app e documentacao do projeto`
