package design.patterns.creational.singleton

class SecondSingleton private constructor(){
    companion object {
        private var singleton: SecondSingleton? = null
        fun getSecondSingleTon(): SecondSingleton? {
            if (singleton == null) {
                singleton = SecondSingleton()
            }
            return singleton
        }
    }
}