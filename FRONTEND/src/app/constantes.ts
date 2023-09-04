export interface Idioma {
    idiomaId: number,
    habla: string,
    escribe: string,
    lee: string
}

export interface Familiar {
    nombreFamiliar: string,
    parentesco: string,
    fechaNacimiento: string,
    telefono: string,
    vive: string,
    profesion: string, 
    trabaja: string,
    lugarTrabajo: string,
    dependeEconomicamente: string
}

export interface FamiliarLaborandoOJ {
    nombreCompleto: string,
    parentesco: string,
    dependencia: string,
    puesto: string
}

export interface PasantiaOJ {
    dependencia: string,
    fechaInicio: string,    
    fechaFinalizacion: string,
    secretarioJuez: string,
    registrada: string
}

export interface ExperienciaLaboralOJ {
    dependencia: string,
    fechaInicio: string,    
    fechaFinalizacion: string,
    jefeInmediato: string,
    puesto: string, 
    renglonPresupuestario: string, 
    motivoFinRelacionLaboral: string
}

export interface ExperienciaLaboral {
    institucionEmpresa: string,
    fechaInicio: string,    
    fechaFinalizacion: string,
    renglonPresupuestario: string,
    puesto: string, 
    jefeInmediato: string,
    telefono: string,
    motivoFinRelacionLaboral: string
}

export interface ReferenciaPersonal {
    nombre: string,
    tipoRelacion: string,    
    aniosConocerlo: string,
    telefono: string
}

export const ConvocatoriaEjemplo = [
    {
        INFO: 'Coordinar la Unidad de Control, Seguimiento y Evaluación de los Órganos Especializados en Delitos de Femicidio y Otras Formas de Violencia contra la Mujer del Organismo Judicial, para cumplir con las metas de la dependencia.'
    },
    {
        INFO: 'Licenciatura en Ciencias Jurídicas y Sociales, Abogado y Notario; Administración de Empresas, Ciencias Políticas u otra carrera afín al puesto. Colegiado Activo. Indispensable y comprobable. Estudios de Maestría en carrera afín al puesto. Opcional.'
    },
    {
        INFO: '4 años o más de experiencia laboral en funciones relacionadas con el puesto (todo lo concerniente en Materia de Delitos de Femicidio y Otras Formas de Violencia contra la Mujer). Indispensable y comprobable. Manejo de personal. Indispensable y comprobable. Presentación de informes. Indispensable.'
    },
    {
        INFO: 'Aplicación de Leyes y Reglamentos relacionados al puesto (ordenamiento jurídico en materia de Delitos de Femicidio y Otras Formas de Violencia contra la Mujer) Indispensable y comprobable. Plan Operativo Anual (POA). \n Victimología Opcional.'
    },
    {
        INFO: 'Toma de decisiones, liderazgo, responsabilidad, supervisión, empatía, discreción, dinamismo, compromiso, proactividad, adecuadas relaciones interpersonales, orientación al servicio a usuarios y a la obtención de resultados. Comunicación verbal y escrita.'
    },
    {
        INFO: 'Análisis, Síntesis e Interpretación en Materia Jurídica Indispensable. Razonamiento lógico. Indispensable. Manejo de equipo de oficina y de equipo de cómputo en ambiente Windows y Office. Indispensable. Trabajo en equipo multidisciplinario. Negociación para consensos.'
    },
    {
        INFO: 'Adecuada presentación personal. Disponibilidad de horario.No haber incurrido en faltas o delitos en la administración pública Indispensable y comprobable. DOCUMENTACIÓN A PRESENTAR'
    }

];

export const Convocatorias = [
    {
        TITULO: 'Convocatoria Externa No. 004-2022/lcdz',
        DESCRIPCION: 'Convocatoria Externa TRADUCTOR E INTÉRPRETE DE IDIOMAS DE PUEBLOS INDÍGENAS (Oficial I)'
    },
    {
        TITULO: 'Convocatoria Externa No. 005-2022/lcdz',
        DESCRIPCION: 'Convocatoria Externa PEDAGOGO. Juzgados de Primera Instancia de la Niñez y Adolescencia y Adolescentes en Conflicto con la Ley Penal/Juzgados de Primera Instancia de la Niñez y Adolescencia del Área Metropolitana.'
    },
    {
        TITULO: 'Convocatoria Externa No. 006-2022/lcdz',
        DESCRIPCION: 'Convocatoria Externa PSICÓLOGA. Tribunal de Sentencia Penal de Delitos de Femicidio y Otras Formas de Violencia contra la Mujer y Violencia Sexual/ Juzgado de Primera Instancia Penal de Delitos de Femicidio y Otras Formas de Violencia contra la Mujer y Violencia Sexual/Sistema de Atención Integral'
    },
    {
        TITULO: 'Convocatoria Externa No. 007-2022/lcdz',
        DESCRIPCION: 'Convocatoria Externa de PSICÓLOGO (A). Juzgado de Primera Instancia de la Niñez y Adolescencia y Adolescentes en Conflicto con la Ley Penal'
    },
    {
        TITULO: 'Convocatoria Externa No. 008-2022/lcdz',
        DESCRIPCION: 'Convocatoria Externa PSICÓLOGO (A). Sala de la Corte de Apelaciones de la Niñez y Adolescencia/Juzgados de Primera Instancia de Familia'
    },
    {
        TITULO: 'Convocatoria Externa No. 010-2022/lcdz',
        DESCRIPCION: 'Convocatoria Externa TRABAJADOR (A) SOCIAL. Tribunales de Sentencia Penal de Delitos de Femicidio y Otras Formas de Violencia contra la Mujer y Violencia Sexual/ Juzgados de Primera Instancia Penal de Delitos de Femicidio y Otras Formas de Violencia contra la Mujer y Violencia Sexual/Sistema de Atención Integral'
    } ,
    {
        TITULO: 'Convocatoria Externa No. 007-2021/lcdz',
        DESCRIPCION: '	AGENTE DE SEGURIDAD (MUJERES) Régimen de Servicio: Libre Nombramiento y Remoción'
    } 
];

export const PerfilesPersonas = [
    {
        DPI: '30585874450101',
        NOMBRE: 'José Daniel Lorenzo Ajcip'        
    },
    {
        DPI: '3050583390117',
        NOMBRE: 'María de Los Angeles Herrera Sumalé'        
    },
    {
        DPI: '3058749660117',
        NOMBRE: 'Maria Fernanda Herrera Sumalé'        
    },
    {
        DPI: '30585874550101',
        NOMBRE: 'Luis Felipe Gonzalez Orellana'
        
    },
    {
        DPI: '2996683390101',
        NOMBRE: 'Edgar Roberto Herrera Cuca'
        
    }

    
];

export const Sexo = [
    {
        ID: 0,
        DESCRIPCION: 'FEMENINO'
    },
    {
        ID: 1,
        DESCRIPCION: 'MASCULINO'
    }
]

export const TiposLicencia = [
    {
        ID: 0,
        DESCRIPCION: 'NINGUNA'
    },
    {
        ID: 1,
        DESCRIPCION: 'A'
    },
    {
        ID: 2,
        DESCRIPCION: 'B'
    },
    {
        ID: 3,
        DESCRIPCION: 'C'
    }, 
    {
        ID: 4,
        DESCRIPCION: 'E'
    }, 
    {
        ID: 5,
        DESCRIPCION: 'M'
    }
]

export const EstadoCivil = [
    {
        ID: 0,
        DESCRIPCION: 'SOLTERO(A)'
    },
    {
        ID: 1,
        DESCRIPCION: 'CASADO(A)'
    },
    {
        ID: 2,
        DESCRIPCION: 'UNION LEGALMENTE DECLARADA'
    },
    {
        ID: 3,
        DESCRIPCION: 'OTRO'
    }
]

export const Discapacidad = [
    {
        ID: 0,
        DESCRIPCION: 'NINGUNA'
    },
    {
        ID: 1,
        DESCRIPCION: 'AUDITIVA'
    },
    {
        ID: 2,
        DESCRIPCION: 'VISUAL'
    },
    {
        ID: 3,
        DESCRIPCION: 'FÍSICA'
    }, 
    {
        ID: 4,
        DESCRIPCION: 'MÚLTIPLE'
    }
]

export const Aplicaciones = [
    {
        TITULO: 'Convocatoria Externa No. 004-2022/lcdz',
        ESTADO: 'Rechazada'
    },
    {
        TITULO: 'Convocatoria Externa No. 005-2022/lcdz',
        ESTADO: 'Pendiente de revisión'
    },
    {
        TITULO: 'Convocatoria Externa No. 006-2022/lcdz',
        ESTADO: 'Aprobada'
    }
]

export const Raza = [
    {
        ID: 0,
        DESCRIPCION: 'MAYA'
    },
    {
        ID: 1,
        DESCRIPCION: 'GARIFUNA'
    },
    {
        ID: 2,
        DESCRIPCION: 'XINCA'
    },
    {
        ID: 3,
        DESCRIPCION: 'LADINO O MESTIZO'
    }, 
    {
        ID: 4,
        DESCRIPCION: 'AFRODESCENDIENTE'
    },
    {
        ID: 5,
        DESCRIPCION: 'OTRO'
    }
]