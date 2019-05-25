(module 
	
	;;hash[7] 				+ (rightRotate(e, 6) ^ rightRotate(e, 11) ^ rightRotate(e, 25)) // S1 				+ ((e&hash[5])^((~e)&hash[6])) // ch 				+ k[i] 				// Expand the message schedule if needed 				+ (w[i] = (i < 16) ? w[i] : ( 						w[i - 16] 						+ (rightRotate(w15, 7) ^ rightRotate(w15, 18) ^ (w15>>>3)) // s0 						+ w[i - 7] 						+ (rightRotate(w2, 17) ^ rightRotate(w2, 19) ^ (w2>>>10)) // s1 					)|0 				)
	
	;;Subtree size 88
	
	(func $Swagwoclaeeicl (param  i32) (param  i32) (param  i32) (param  i32) (param  i32) (param  i32) (param  i32) (param  i32) (param  i32) (param  i32) (result i32) 
		
		;;hash[7]
		get_local 0
		
		;;rightRotate(e, 6)
		get_local 1
		
		;;rightRotate(e, 11)
		get_local 2
		i32.xor
		
		;;rightRotate(e, 25)
		get_local 3
		i32.xor
		i32.add
		
		;;e
		get_local 4
		
		;;hash[5]
		get_local 5
		i32.and
		
		;;~e
		get_local 6
		
		;;hash[6]
		get_local 7
		i32.and
		i32.xor
		i32.add
		
		;;k[i]
		get_local 8
		i32.add
		
		;;w[i] = (i < 16) ? w[i] : ( 						w[i - 16] 						+ (rightRotate(w15, 7) ^ rightRotate(w15, 18) ^ (w15>>>3)) // s0 						+ w[i - 7] 						+ (rightRotate(w2, 17) ^ rightRotate(w2, 19) ^ (w2>>>10)) // s1 					)|0
		get_local 9
		i32.add
		
	)
	(export "Swagwoclaeeicl" (func $Swagwoclaeeicl))
)