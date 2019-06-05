CREATE OR REPLACE FUNCTION public.feed_ngram1(se text, p text, d Timestamp, n int)
 RETURNS void AS $BODY$
DECLARE
    chunk int[];
    i int;
    tokens_size int;
    key_count int;
BEGIN

    -- initializing
    i:=0;
    SELECT array_length(tokens, 1) INTO tokens_size FROM public."File" WHERE session=se AND session_date=d AND path=p;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'session % file % not found', se, p;
    END IF;

    IF n > tokens_size THEN
        RAISE EXCEPTION 'NGram size greater than trace size';
    END IF;

    LOOP

       RAISE INFO 'position %',i;
       -- Getting the ngram
       SELECT tokens[i + 1: i + n] INTO chunk FROM public."File" WHERE session=se AND session_date=d AND path=p;

       SELECT COUNT(*) INTO key_count FROM public."NGram"
            WHERE seq=chunk;

       IF key_count > 0 THEN -- There is ngram
            UPDATE public."NGram"
            SET ocurrencies = array_append(ocurrencies, i)
            WHERE seq=chunk;
       ELSE
            INSERT INTO public."NGram" VALUES(n, chunk, ARRAY[i], p, se, d);
       END IF;

       RAISE INFO 'chunk %',chunk;
       i:= i + 1;
       EXIT WHEN i = tokens_size - n + 1;
    END LOOP;

    RAISE INFO 'position %',tokens_size;
END;
$BODY$ LANGUAGE plpgsql;
