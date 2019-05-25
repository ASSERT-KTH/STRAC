(module 
	
	;;primeCounter < 64
	
	;;Subtree size 3
	
	(func $Zeoknogibriu (param  i32) (result i32) 
		
		;;primeCounter
		get_local 0
		i32.const 64
		i32.lt_u
		
	)
	(export "Zeoknogibriu" (func $Zeoknogibriu))
)