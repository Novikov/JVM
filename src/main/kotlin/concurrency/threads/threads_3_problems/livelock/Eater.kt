package concurrency.threads.threads_3_problems.livelock

class Eater(val name: String, var isHungry: Boolean = true) {

    fun eatWith(spoon: Spoon, spouse: Eater) {
        while (isHungry) {
            // Don't have the spoon, so wait patiently for spouse.
            if (spoon.owner != this) {
                try {
                    Thread.sleep(1)
                } catch (e: InterruptedException) {
                    continue
                }
                continue
            }

            // If spouse is hungry, insist upon passing the spoon.
            if (spouse.isHungry) {
                System.out.printf(
                    "%s: You eat first my darling %s!%n",
                    name, spouse.name
                )
                spoon.changeOwner(spouse)
                continue
            }

            // Spouse wasn't hungry, so finally eat
            spoon.use()
            isHungry = false
            System.out.printf(
                "%s: I am stuffed, my darling %s!%n",
                name, spouse.name
            )
            spoon.changeOwner(spouse)
        }
    }
}