(module 
	
	;;(rightRotate(a, 2) ^ rightRotate(a, 13) ^ rightRotate(a, 22)) // S0 				+ ((a&hash[1])^(a&hash[2])^(hash[1]&hash[2]))
	
	;;Subtree size 34
	
	(func $Iioateaipro (param  i32) (param  i32) (param  i32) (param  i32) (param  i32) (param  i32) (result i32) 
		
		;;rightRotate(a, 2)
		get_local 0
		
		;;rightRotate(a, 13)
		get_local 1
		i32.xor
		
		;;rightRotate(a, 22)
		get_local 2
		i32.xor
		
		;;a
		get_local 3
		
		;;hash[1]
		get_local 4
		i32.and
		
		;;a
		get_local 3
		
		;;hash[2]
		get_local 5
		i32.and
		i32.xor
		
		;;hash[1]
		get_local 4
		
		;;hash[2]
		get_local 5
		i32.and
		i32.xor
		i32.add
		
	)
	(export "Iioateaipro" (func $Iioateaipro))
)