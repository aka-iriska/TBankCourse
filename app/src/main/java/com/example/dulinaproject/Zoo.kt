package com.example.dulinaproject

/**
 * Для зоо-магазина требуется спроектировать сущности в коде, чтобы в дальнейшем использовать их для продажи корма и игрушек.
 * Пока магазин продает только корма для определенных пород животных:
 * [Husky],
 * [Corgi],
 * [Scottish] и
 * [Siam] котов.
 * У каждого животного есть [IAnimal.weight] и [IAnimal.age], это нужно, чтобы подбирать только определенные корма и игрушки для них.
 * Также у каждой породы собаки есть определенный прикус: [Bite.STRAIGHT], [Bite.OVER] или [Bite.UNDER],
 * а у кошек тип поведения: [Behavior.ACTIVE] или [Behavior.PASSIVE].
 * Спроектируйте интерфейсы и реализуйте их для каждой породы собак и кошек.
 * Также спроектируйте класс зоо-магазина [ZooStore] с методом [ZooStore.getBreed],
 * который будет определять собака или кошка в зависимости от породы, переданной в аргументах метода.
 */

// --------Интерфейсы------------

interface IAnimal {
    val weight:Int
    val age: Int

    fun getCharacteristics():String{
        return "вес $weight возраст $age"
    }
}

enum class Bite{
    STRAIGHT, OVER, UNDER
}

interface IDog: IAnimal{
    val biteType: Bite

    override fun getCharacteristics(): String {
        return super.getCharacteristics() + " прикус $biteType"
    }
}

enum class Behavior{
    ACTIVE, PASSIVE
}

interface ICat: IAnimal{
    val behaviourType: Behavior

    override fun getCharacteristics(): String {
        return super.getCharacteristics() + " поведение $behaviourType"
    }
}

// ------------Реализация интерфейсов-------

class Husky(
    override val weight: Int,
    override val age: Int,
    override val biteType: Bite
): IDog{}

class Corgi(
    override val weight: Int,
    override val age: Int,
    override val biteType: Bite
):IDog{}

class Scottish(
    override val weight: Int,
    override val age: Int,
    override val behaviourType: Behavior
):ICat{}

class Siam(
    override val weight: Int,
    override val age: Int,
    override val behaviourType: Behavior
):ICat{}

class ZooStore(){
    fun getBreed(breed: IAnimal) : String{
        return when(breed){
            is Husky -> "Cобака: Хаски ${breed.getCharacteristics()}"
            is Corgi -> "Cобака: Корги ${breed.getCharacteristics()}"
            is Scottish -> "Кошка: Шотландская ${breed.getCharacteristics()}"
            is Siam -> "Кошка: Сиамская ${breed.getCharacteristics()}"
            else -> "Порода не опознана"
        }
    }
}

// -------MAIN----------

fun main(){
    val store = ZooStore()
    val husky = Husky(10, 10, Bite.OVER)
    val husky2 = Husky(10,   2, Bite.STRAIGHT)
    val siam = Siam(12, 8, Behavior.PASSIVE)
    val scottish = Scottish(8, 7, Behavior.ACTIVE)
    println(store.getBreed(husky))
    println(store.getBreed(husky2))
    println(store.getBreed(siam))
    println(store.getBreed(scottish))
}
