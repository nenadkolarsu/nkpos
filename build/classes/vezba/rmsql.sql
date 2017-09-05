SELECT DISTINCT ON (prezime, ime) * FROM ( 
	SELECT 
	  rm.oj                AS sifoj, 
	  of.naziv             AS nazivoj, 
	  lp.jmbg, 
	  lp.prezime, 
	  lp.ime, 
	  btrim(lp.e_mail[1])  AS e_mail 
	FROM ( 
	  SELECT DISTINCT ON (sifra) sifra, oj 
	  FROM pionir_2017_kadrovi.radno_mesto 
	  WHERE vazeci AND datum_radnog_mesta <= '2017-04-30' 
	  ORDER BY sifra, datum_radnog_mesta DESC, ts DESC 
	) AS rm 
	  JOIN pionir_2017_kadrovi.licni_podaci AS lp 
	  ON lp.aktivan AND lp.vazeci AND rm.sifra = lp.jmbg 
	    JOIN pionir_2017_kadrovi.radni_staz AS rs 
	    ON lp.jmbg = rs.sifra AND rs.aktivan AND rs.vazeci 
	    AND (rs.datum_prekid_rad <= '1900-01-01' OR rs.datum_prekid_rad >= '2017-04-1') 
	      JOIN pionir_2017_plate.obracuni_redovna_plata as orp 
	      ON lp.jmbg = orp.jmbg and orp.aktivan and orp.vazeci and orp.datum_obracuna = '2017-04-30' 
	      AND orp.tip_obacuna_plate = 'RO' AND orp.datum_isplate = '2017-05-18' 
	      AND orp.brojnal = '' AND orp.status = 'ISPLACEN' 
	        JOIN pionir_2017_sifarnici.organizacija_firme AS of 
	        ON rm.oj = of.sifra AND of.aktivan AND of.vazeci 
	WHERE true  
	) AS r 
	WHERE e_mail = '' 
	ORDER BY prezime, ime 
