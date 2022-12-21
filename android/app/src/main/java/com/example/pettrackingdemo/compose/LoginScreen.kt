//import android.view.Surface
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.material.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.res.painterResource
//import androidx.compose.foundation.Image
//
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.pettrackingdemo.R
//import com.example.projectlogin.ui.theme.ProjectLoginTheme
//
//@Composable
//fun loginScreen() {
//    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//        Column(
//            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            WelcomeText()
//            PurposeImage()
//            text_field(
//                InputType = KeyboardType.Email,
//                "E-mail address",
//                IconImage = painterResource(id = R.drawable.user_login)
//            )
//            SignIn()
//            ForgotPasswordText()
//        }
//    }
//}
//
//@Composable
//fun WelcomeText() {
//    Text(
//        text = "Welcome To Maps",
//        color = Color.White,
//        fontSize = 25.sp,
//        modifier = Modifier.padding(top = 40.dp)
//    )
//}
//
//@Composable
//fun PurposeImage() {
//    Image(
//        painter = painterResource(id = R.drawable.location), contentDescription = "LocationPin",
//        modifier = Modifier.size(300.dp)
//    )
//}
//
//@Composable
//fun text_field(InputType: KeyboardType, placeholder: String, IconImage: Painter) {
//    var TextFieldEmailState = remember { mutableStateOf("") }
//
//    TextField(
//        value = TextFieldEmailState.value,
//        onValueChange = { newInput -> TextFieldEmailState.value = newInput },
//        leadingIcon = {
//            Image(
//                painter = painterResource(id = R.drawable.user_login),
//                contentDescription = "email"
//            )
//        },
//        label = { Text(text = "E-mail address", color = MaterialTheme.colors.TextFieldColor) },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//        modifier = Modifier
//            .padding(top = 25.dp)
//            .background(color = MaterialTheme.colors.TextFieldColor)
//    )
//    var TextFieldPasswordState = remember { mutableStateOf("") }
//    TextField(
//        value = TextFieldPasswordState.value,
//        onValueChange = { newInput -> TextFieldPasswordState.value = newInput },
//        leadingIcon = {
//            Image(
//                painter = painterResource(id = R.drawable.user_login),
//                contentDescription = "password"
//            )
//        },
//        label = { Text(text = "Password", color = MaterialTheme.colors.TextFieldTextColor) },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        modifier = Modifier
//            .padding(top = 25.dp)
//            .background(color = MaterialTheme.colors.TextFieldColor)
//    )
//}
//
//@Composable
//fun SignIn() {
//    Button(
//        onClick = {}, modifier = Modifier
//            .padding(top = 25.dp)
//            .requiredWidth(277.dp)
//    ) {
//        Text(text = "Sign In")
//    }
//}
//
//@Composable
//fun ForgotPasswordText() {
//    Text(
//        text = "Forgot Password ?", color = MaterialTheme.colors.TextFieldTextColor,
//        modifier = Modifier.padding(top = 70.dp)
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ProjectLoginTheme {
//        loginScreen()
//    }
//}