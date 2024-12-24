import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddressViewModel : ViewModel() {
    private val _addresses = MutableStateFlow(
        listOf(
            "ул. Пушкина, д. 10, кв. 5, Москва",
            "пр. Ленина, д. 22, офис 101, Санкт-Петербург",
            "ул. Гагарина, д. 8, кв. 15, Казань",
            "пер. Цветочный, д. 4, кв. 12, Екатеринбург",
            "ул. Советская, д. 1, Омск",
            "ул. Зеленая, д. 33, кв. 7, Новосибирск",
            "ул. Центральная, д. 9, кв. 18, Краснодар",
            "ул. Береговая, д. 5, Сочи",
            "ул. Вокзальная, д. 11, кв. 3, Самара",
            "ул. Кирова, д. 15, кв. 6, Ростов-на-Дону"
        )
    )
    val addresses: StateFlow<List<String>> = _addresses

    private val _selectedAddress = MutableStateFlow<String?>("ул. Пушкина, д. 10, кв. 5, Москва")
    val selectedAddress: StateFlow<String?> = _selectedAddress

    fun selectAddress(address: String) {
        _selectedAddress.value = address
    }
}