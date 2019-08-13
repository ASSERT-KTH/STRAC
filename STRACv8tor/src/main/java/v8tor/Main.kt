package v8tor

import org.apache.commons.cli.*
import strac.core.stream_providers.CommandStdInputProvider
import java.io.ByteArrayInputStream
import java.io.File


fun main(args: Array<String>){

    val options = Options()



    options.addOption(Option.builder("u")
            .hasArg(true)
            .desc("Site address")
            .required()
            .longOpt("url")
            .build()
    )
    options.addOption(Option.builder("b")
            .hasArg(true)
            .desc("V8 binary address")
            .required()
            .longOpt("bin")
            .build()
    )

    options.addOption(Option.builder("t")
            .hasArg(true)
            .desc("Session time in seconds")
            .longOpt("time")
            .type(Int.javaClass)
            .build()
    )

    val parser = DefaultParser()


    try {
        val cmd = parser.parse(options, args)

        runCatching { File("temp").delete() }

        val runChrome = "%s --no-sandbox -user-data-dir=%s --js-flags=\"--print-bytecode --trace-ignition\"  %s"
                .format(
                        cmd.getOptionValue("b"),
                        "temp",
                        cmd.getOptionValue("u"))

        val runner = CommandStdInputProvider()


        if(cmd.hasOption("t"))
            runner.setMaxTime(Integer.parseInt(cmd.getOptionValue("t")))

        println("Getting output for %s".format(runChrome))

        //val byteStr = ByteArrayInputStream(.readAllBytes())
        var count = 0



        //val byteStr = ByteArrayInputStream(test.toByteArray())

        println("Analyzing bytecode")

        val sc = java.util.Scanner(runner.getStream(runChrome)).useDelimiter("\n")
        val map = HashMap<String, Int>()



        val patternStatic = "^(( )*\\d+ [ES]>)?( *)(0x\\w+) @"
        val patternDynamic = "^ ->( *)(0x\\w+) @"

        while(sc.hasNext()){
            val line = sc.next()

            if(Regex(patternStatic).containsMatchIn(line)){
                val address =Regex(patternStatic).find(line)?.groups!![4]?.value?: "UNKNOWN"

                map[address] = 0
                //println(line)
            }
            else if(Regex(patternDynamic).containsMatchIn(line)){

                val address =Regex(patternDynamic).find(line)?.groups!![2]?.value?: "UNKNOWN"

                if(address in map) {
                    map[address] = map[address]?.plus(1)?:-1
                }
            }else{
                print("Not alanyzed ")
                println(line)
            }

        }

        val total = map.filter { it.value >= 0 }.size
        val covered = map.map { it.value }.filter { it > 0 }.size
        val weird = map.map { it.value }.filter { it ==-1 }.size

        println("Static sentences: %s".format(total))
        println("Covered sentences: %s".format(covered))
        println("Weird sentences: %s".format(weird))
        println("Coverage: %s".format(1.0*covered/total))

        runCatching { File("temp").delete() }
    } catch (exp: ParseException) {
        println("Parsing failed.  Reason: " + exp.message)

        val formatter = HelpFormatter()
        formatter.printHelp("STRAC-v8tor", options)
    }


}
