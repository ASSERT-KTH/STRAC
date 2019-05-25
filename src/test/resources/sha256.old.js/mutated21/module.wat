(module 
	
	;;(hash[i] + oldHash[i])|0
	
	;;Subtree size 9
	
	(func $Diaoyoroee (param  i32) (param  i32) (result i32) 
		
		;;hash[i]
		get_local 0
		
		;;oldHash[i]
		get_local 1
		i32.add
		i32.const 0
		i32.or
		
	)
	(export "Diaoyoroee" (func $Diaoyoroee))
)