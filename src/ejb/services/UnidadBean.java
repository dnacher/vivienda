package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import static java.lang.Math.toIntExact;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import entities.hibernate.SessionConnection;
import org.hibernate.StatelessSession;
import entities.constantes.Constantes;
import entities.constantes.ConstantesEtiquetas;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Daniel
 */
public class UnidadBean {

	public Transaction tx;
	public boolean correcto;
	SessionConnection sc;

	public UnidadBean() {
		try {
			sc = new SessionConnection();
			tx = sc.useSession().beginTransaction();
			correcto = false;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	public boolean guardar(Unidad unidad) throws ServiceException {
		correcto = false;
		try {
			sc.useSession().save(unidad);
			tx.commit();
			sc.closeSession();
			correcto = true;
			ConfiguracionControl.ActualizaId(ConstantesEtiquetas.UNIDAD);
		} catch (Exception ex) {
			throw new ServiceException(ex.getCause().getMessage());
		}
		return correcto;
	}

	public boolean guardarUnidades(List<Unidad> lista) throws ServiceException, IOException {
		correcto = false;
		FileWriter outfile = null;
		outfile = new FileWriter(Constantes.PATH);
		String outputString = "";
		try {
			StatelessSession session = sc.useStatelessSession();
			tx = session.beginTransaction();
			for (Unidad unidad : lista) {
				try {
					session.insert(unidad);
					outputString += unidad.getNombre() + " " + unidad.getApellido() + " " + unidad.getBlock() + unidad.getTorre() + " " + unidad.getPuerta() + " OK\n";
				} catch (Exception ex) {
					outputString += unidad.getNombre() + " " + unidad.getApellido() + " " + unidad.getBlock() + unidad.getTorre() + " " + unidad.getPuerta() + " ERROR\n";
					outputString += "ERROR: " + ex.getMessage() + "\n";
				}
			}
			tx.commit();
			session.close();
			correcto = true;
			int ultId = lista.get(lista.size() - 1).getIdUnidad() + 1;
			ConfiguracionControl.ActualizaIdXId(ConstantesEtiquetas.UNIDAD, ultId);
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage());
		}
		outfile.write(outputString);
		outfile.flush();
		return correcto;
	}

	public boolean eliminar(Unidad unidad) throws ServiceException {
		try {
			unidad.setActivo(false);
			sc.useSession().update(unidad);
			tx.commit();
			sc.closeSession();
			correcto = true;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage());
		}
		return correcto;
	}

	public boolean modificar(Unidad unidad) throws ServiceException {
		try {
			sc.useSession().update(unidad);
			tx.commit();
			sc.closeSession();
			correcto = true;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage());
		}
		return correcto;
	}

	public List<Unidad> traerTodos() throws ServiceException {
		try {
			Query query = sc.useSession().createQuery("FROM Unidad");
			List<Unidad> unidades = query.list();
			sc.closeSession();
			return unidades;
		} catch (HibernateException ex) {
			throw new ServiceException(ex.getMessage());
		}
	}

	public List<Unidad> TraeUnidadesXBlockTorre(String block, int torre) throws ServiceException {
		List<Unidad> lista = new ArrayList<>();
		String consulta = "SELECT unidad FROM Unidad unidad ";
		int sinFiltro = 0;
		try {
			if (torre == 0) {
				if (!block.isEmpty()) {
					consulta += "WHERE Block=:block ";
					sinFiltro = 1;
				}
			} else if (block.isEmpty()) {
				consulta += "WHERE Torre=:torre ";
			} else {
				consulta += "WHERE Block=:block "
					+ "AND Torre=:torre ";
				sinFiltro = 1;
			}
			if (sinFiltro == 0) {
				consulta += "WHERE unidad.activo=true";
			} else {
				consulta += "AND unidad.activo=true";
			}
			Query query = sc.useSession().createQuery(consulta);
			if (!block.isEmpty()) {
				query.setParameter("block", block);
			}
			if (torre != 0) {
				query.setParameter("torre", torre);
			}
			lista = query.list();
			sc.closeSession();
		} catch (HibernateException ex) {
			throw new ServiceException(ex.getMessage());
		}
		return lista;
	}

	public Unidad TraeUnidadesXBlockTorrePuerta(Unidad unidad) throws ServiceException {
		Unidad uni;
		try {
			String consulta = "SELECT unidad FROM Unidad unidad "
				+ "WHERE Block=:block "
				+ "AND Torre=:torre "
				+ "AND Puerta=:puerta "
				+ "AND unidad.activo=true";
			Query query = sc.useSession().createQuery(consulta);
			query.setParameter("block", unidad.getBlock());
			query.setParameter("torre", unidad.getTorre());
			query.setParameter("puerta", unidad.getPuerta());
			uni = (Unidad) query.uniqueResult();
			sc.closeSession();
		} catch (HibernateException ex) {
			throw new ServiceException(ex.getMessage());
		}
		return uni;
	}

	public Unidad traerUnidadXId(int Id) throws ServiceException {
		Query query = sc.useSession().createQuery("FROM Unidad unidad WHERE unidad.IdUnidad=:id");
		query.setParameter("id", Id);
		Unidad unidades = (Unidad) query.uniqueResult();
		sc.closeSession();
		return unidades;
	}

	public int totalUnidadesNoedificios(String block, Integer torre) {
		int retorno = 0;
		if (block == null && torre == null) {
			Query query = sc.useSession().createQuery("SELECT COUNT(*) FROM Unidad unidad");
			Long count = (Long) query.uniqueResult();
			retorno = toIntExact(count);
			sc.closeSession();
			return retorno;
		} else if (block == null && torre != null) {
			Query query = sc.useSession().createQuery("SELECT COUNT(*) FROM Unidad unidad "
				+ "WHERE unidad.torre=:laTorre "
				+ "AND unidad.activo=true "
				+ "AND unidad.esEdificio = false "
				+ "OR unidad.esEdificio = NULL");
			query.setParameter("laTorre", torre);
			Long count = (Long) query.uniqueResult();
			retorno = toIntExact(count);
			sc.closeSession();
		} else if (block != null && torre == null) {
			Query query = sc.useSession().createQuery("SELECT COUNT(*) FROM Unidad unidad "
				+ "WHERE unidad.block=:elBlock "
				+ "AND unidad.activo=true "
				+ "AND unidad.esEdificio = false OR unidad.esEdificio = NULL");
			query.setParameter("elBlock", block);
			Long count = (Long) query.uniqueResult();
			retorno = toIntExact(count);
			sc.closeSession();
		} else {
			Query query = sc.useSession().createQuery("SELECT COUNT(*) FROM Unidad unidad "
				+ "WHERE unidad.block=:elBlock "
				+ "AND unidad.torre=:laTorre "
				+ "AND unidad.activo=true "
				+ "AND unidad.esEdificio = false "
				+ "OR unidad.esEdificio = NULL");
			query.setParameter("elBlock", block);
			query.setParameter("laTorre", torre);
			Long count = (Long) query.uniqueResult();
			retorno = toIntExact(count);
			sc.closeSession();
		}
		return retorno;
	}

	public int totalUnidadesNoPago(String block, int torre) {
		if (block.equals("") && torre == 0) {
			Query query = sc.useSession().createQuery("SELECT COUNT(*) FROM Unidad unidad");
			Long count = (Long) query.uniqueResult();
			int retorno = toIntExact(count);
			sc.closeSession();
			return retorno;
		} else {
			Query query = sc.useSession().createQuery("SELECT COUNT(*) FROM Unidad unidad "
				+ "WHERE unidad.block=:elBlock "
				+ "AND unidad.torre=:laTorre "
				+ "AND unidad.idUnidad NOT IN ("
				+ "SELECT gastoscomunes.unidad "
				+ "FROM Gastoscomunes gastoscomunes "
				+ "WHERE gastoscomunes.periodo=:periodo "
				+ "AND gastoscomunes.estado=:est) "
				+ "AND unidad.esEdificio = false "
				+ "OR unidad.esEdificio = NULL");
			query.setParameter("est", 2);
			query.setParameter("periodo", ConfiguracionControl.devuelvePeriodoActual());
			query.setParameter("elBlock", block);
			query.setParameter("laTorre", torre);
			Long count = (Long) query.uniqueResult();
			int retorno = toIntExact(count);
			sc.closeSession();
			return retorno;
		}
	}

	public List<Unidad> TraeUnidadesGastosComunesNoPago() {
		List<Unidad> list = new ArrayList<>();
		try {
			Query query = sc.useSession().createQuery("SELECT unidad FROM Unidad unidad "
				+ "WHERE unidad.idUnidad NOT IN ("
				+ "SELECT gastoscomunes.unidad "
				+ "FROM Gastoscomunes gastoscomunes "
				+ "WHERE gastoscomunes.periodo=:periodo "
				+ "AND gastoscomunes.estado=:est) "
				+ "AND unidad.esEdificio = false OR unidad.esEdificio = NULL");
			query.setParameter("est", Constantes.NO_PAGO);
			query.setParameter("periodo", ConfiguracionControl.devuelvePeriodoActual());
			list = query.list();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			sc.closeSession();
		}
		return list;
	}

	public List<Unidad> TraeUnidadesGastosComunesNoCargadas(int anio, int mes) {
		List<Unidad> list = new ArrayList<>();
		try {
			Query query = sc.useSession().createQuery("SELECT unidad FROM Unidad unidad "
				+ "WHERE unidad.idUnidad NOT IN ("
				+ "SELECT gastoscomunes.unidad "
				+ "FROM Gastoscomunes gastoscomunes "
				+ "WHERE gastoscomunes.periodo=:periodo "
				+ "AND (gastoscomunes.estado=:est "
				+ "OR gastoscomunes.estado=:est2)) "
				+ "AND (unidad.esEdificio = false "
				+ "OR unidad.esEdificio = NULL)");
			query.setParameter("est", Constantes.PAGO);
			query.setParameter("est2", Constantes.NO_PAGO);
			query.setParameter("periodo", ConfiguracionControl.traePeriodo(anio, mes));
			list = query.list();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			sc.closeSession();
		}
		return list;
	}

//    public List<Unidad> TraeUnidadesXBlockTorreNoPago(String block, int torre) {
//        List<Unidad> lista = new ArrayList<>();
//        String consulta = "SELECT unidad FROM Unidad unidad ";
//        int sinFiltro = 0;
//        try {
//            if (torre == 0) {
//                if (!block.isEmpty()) {
//                    consulta += "WHERE Block=:block ";
//                    sinFiltro = 1;
//                }
//            } else if (block.isEmpty()) {
//                consulta += "WHERE Torre=:torre ";
//                sinFiltro = 1;
//            } else {
//                consulta += "WHERE Block=:block "
//                        + "AND Torre=:torre ";
//                sinFiltro = 1;
//            }
//            if (sinFiltro == 0) {
//                consulta += "WHERE unidad.activo=true ";
//            } else {
//                consulta += "AND unidad.activo=true ";
//            }
//            consulta += "AND unidad.idUnidad NOT IN (SELECT gastoscomunes.unidad "
//                    + "FROM Gastoscomunes gastoscomunes "
//                    + "WHERE gastoscomunes.periodo=:periodo "
//                    + "AND gastoscomunes.estado=:est) "
//                    + "AND unidad.esEdificio = false OR unidad.esEdificio = NULL";
//            Query query = sc.useSession().createQuery(consulta);
//            if (!block.isEmpty()) {
//                query.setParameter("block", block);
//            }
//            if (torre != 0) {
//                query.setParameter("torre", torre);
//            }
//            query.setParameter("periodo", ConfiguracionControl.devuelvePeriodoActual());
//            //estado 2 pago al estar en el "not in" trae los que estan pagos
//            query.setParameter("est", Constantes.PAGO);
//            lista = query.list();
//            sc.closeSession();
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//        return lista;
//    }
//    public List<Unidad> TraeUnidadesConvenio() {
//        List<Unidad> list = new ArrayList<>();
//        try {
//            Query query = sc.useSession().createQuery("SELECT unidad FROM Unidad unidad "
//                    + "WHERE unidad.idUnidad IN ("
//                    + "SELECT gastoscomunes.unidad "
//                    + "FROM Gastoscomunes gastoscomunes "
//                    + "WHERE gastoscomunes.periodo<:periodo "
//                    + "AND gastoscomunes.estado=:est) "
//                    + "AND unidad.esEdificio = false OR unidad.esEdificio = NULL");
//
//            query.setParameter("est", Constantes.NO_PAGO);
//            query.setParameter("periodo", ConfiguracionControl.devuelvePeriodoActual());
//            list = query.list();
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            sc.closeSession();
//        }
//        return list;
//    }
	public Integer TraeUnidadesConvenioCount() {
		Integer cantidad = -1;
		try {
			Query query = sc.useSession().createQuery("SELECT count(*) FROM Unidad unidad "
				+ "WHERE unidad.idUnidad IN ("
				+ "SELECT gastoscomunes.unidad "
				+ "FROM Gastoscomunes gastoscomunes "
				+ "WHERE gastoscomunes.periodo<:periodo "
				+ "AND gastoscomunes.estado=:est) "
				+ "AND unidad.esEdificio = false OR unidad.esEdificio = NULL");

			query.setParameter("est", Constantes.NO_PAGO);
			query.setParameter("periodo", ConfiguracionControl.devuelvePeriodoActual());
			Long count = (Long) query.uniqueResult();
			cantidad = toIntExact(count);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			sc.closeSession();
		}
		return cantidad;
	}

//    public List<Unidad> TraeUnidadesConvenioXBlockTorre(String block, int torre) {
//        List<Unidad> list = new ArrayList<>();
//        String consulta = "";
//        try {
//            consulta = "SELECT unidad "
//                    + "FROM Unidad unidad ";
//            if (!block.isEmpty()) {
//                consulta += "WHERE unidad.block=:block ";
//                if (torre != 0) {
//                    consulta += "AND unidad.torre=:torre ";
//                }
//            } else if (torre != 0) {
//                consulta += "WHERE unidad.torre=:torre ";
//            }
//            if (block.isEmpty() && torre == 0) {
//                consulta += "WHERE unidad IN (";
//            } else {
//                consulta += "AND unidad IN (";
//            }
//            consulta += "SELECT gastoscomunes.unidad "
//                    + "FROM Gastoscomunes gastoscomunes "
//                    + "WHERE gastoscomunes.periodo<:periodo "
//                    + "AND gastoscomunes.estado=:est) "
//                    + "AND unidad.esEdificio = false OR unidad.esEdificio = NULL";
//            Query query = sc.useSession().createQuery(consulta);
//            query.setParameter("est", Constantes.NO_PAGO);
//            query.setParameter("periodo", ConfiguracionControl.devuelvePeriodoActual());
//            if (!block.equals("")) {
//                query.setParameter("block", block);
//            }
//            if (torre != 0) {
//                query.setParameter("torre", torre);
//            }
//            list = query.list();
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            sc.closeSession();
//        }
//        return list;
//    }
	/**
	 * Trae las unidades en base a los datos que se ingresen
	 *
	 * @param block si en null trae todos los blocks
	 * @param torre si en null trae todas las torres
	 * @param estado si es null trae todos los estados
	 * @param in si es true verifica los in de GC
	 * @param comparaPeriodo constantes.COMPARA_
	 *
	 * @param periodo si en null trae todos los periodos
	 * @param edificios si es true trae con edificios
	 */
	public List<Unidad> traeUnidadesXEstadoXBlockXTorre(String block, Integer torre, boolean in, Integer estado, Integer periodo, Integer comparaPeriodo, boolean edificios) {
		List<Unidad> list = new ArrayList<>();
		String consulta;
		try {
			consulta = "SELECT unidad "
				+ "FROM Unidad unidad ";

			//verifica block y torre
			if (block != null) {
				consulta += "WHERE unidad.block=:block ";
				if (torre != null) {
					consulta += "AND unidad.torre=:torre ";
				}
			} else if (torre != null) {
				consulta += "WHERE unidad.torre=:torre ";
			}
			//verifica el in
			if (in) {
				if (block != null || torre != null) {
					consulta += "AND unidad IN (";
				} else {
					consulta += "WHERE unidad IN (";
				}
			} else {
				if (block != null || torre != null) {
					consulta += "AND unidad NOT IN (";
				} else {
					consulta += "WHERE unidad NOT IN (";
				}
			}

			consulta += "SELECT gastoscomunes.unidad "
				+ "FROM Gastoscomunes gastoscomunes ";

			if (estado != null) {
				consulta += "WHERE gastoscomunes.estado=:estado ";
				if (comparaPeriodo != null && periodo != null) {
					consulta += "AND gastoscomunes.periodo" + devuelveComprarador(comparaPeriodo) + ":periodo)";
				}
			} else if (comparaPeriodo != null && periodo != null) {
				consulta += "WHERE gastoscomunes.periodo" + devuelveComprarador(comparaPeriodo) + ":periodo)";
			}
			if (edificios) {
				consulta += "AND unidad.esEdificio = true";
			} else {
				consulta += "AND unidad.esEdificio = false OR unidad.esEdificio = NULL";
			}
			Query query = sc.useSession().createQuery(consulta);
			if (block != null) {
				query.setParameter("block", block);
			}
			if (torre != null) {
				query.setParameter("torre", torre);
			}
			if (estado != null) {
				query.setParameter("estado", estado);
			}
			if (comparaPeriodo != null && periodo != null) {
				query.setParameter("periodo", periodo);
			}
			list = query.list();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		} finally {
			sc.closeSession();
		}
		return list;
	}

	public String devuelveComprarador(Integer compara) {
		String comparador = "";
		switch (compara) {
			case 0:
				comparador = "=";
				break;
			case 1:
				comparador = ">";
				break;
			case 2:
				comparador = "<";
				break;
			case 3:
				comparador = ">=";
				break;
			case 4:
				comparador = "<=";
				break;
		}
		return comparador;
	}

	public List<Unidad> TraeUnidadesConConvenioXBlockTorre(String block, int torre) {
		List<Unidad> list = new ArrayList<>();
		String consulta = "";
		try {
			consulta = "SELECT unidad "
				+ "FROM Unidad unidad ";
			if (!block.isEmpty()) {
				consulta += "WHERE unidad.block=:block ";
				if (torre != 0) {
					consulta += "AND unidad.torre=:torre ";
				}
			} else if (torre != 0) {
				consulta += "WHERE unidad.torre=:torre ";
			}
			if (block.isEmpty() && torre == 0) {
				consulta += "WHERE unidad IN (";
			} else {
				consulta += "AND unidad IN (";
			}
			consulta += "SELECT convenio.unidad "
				+ "FROM Convenio convenio "
				+ "WHERE convenio.activo=true) "
				+ "AND unidad.esEdificio = false "
				+ "OR unidad.esEdificio = NULL";
			Query query = sc.useSession().createQuery(consulta);
			if (!block.equals("")) {
				query.setParameter("block", block);
			}
			if (torre != 0) {
				query.setParameter("torre", torre);
			}
			list = query.list();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			sc.closeSession();
		}
		return list;
	}

	public List<Unidad> TraeUnidadesConConvenioActivoXBlockTorre(String block, Integer torre) {
		List<Unidad> list = new ArrayList<>();
		String consulta = "";
		try {
			consulta = "SELECT c.unidad "
				+ "FROM Convenio c ";
			if (block != null) {
				consulta += "WHERE c.unidad.block=:block ";
				if (torre != null) {
					consulta += "AND c.unidad.torre=:torre ";
				}
			} else if (torre != null) {
				consulta += "WHERE c.unidad.torre=:torre ";
			}
			if (torre != null || block != null) {
				consulta += "AND c.activo = true "
						  + "AND c.unidad.esEdificio = false";
			} else {
				consulta += "WHERE c.activo = true "
					      + "AND c.unidad.esEdificio = false";
			}

			Query query = sc.useSession().createQuery(consulta);
			if (block != null) {
				query.setParameter("block", block);
			}
			if (torre != null) {
				query.setParameter("torre", torre);
			}
			list = query.list();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			sc.closeSession();
		}
		return list;
	}

	public Long TraeTotalImporteXUnidadParaConvenio(Unidad unidad) {
		Long total = 0L;
		try {
			Query query = sc.useSession().createQuery("SELECT sum(gc.monto_1) as total FROM Gastoscomunes gc "
				+ "WHERE gc.unidad=:unidad "
				+ "AND gc.periodo<:periodo "
				+ "AND gc.estado=:est) "
				+ "AND unidad.esEdificio = false "
				+ "OR unidad.esEdificio = NULL");
			query.setParameter("unidad", unidad);
			query.setParameter("periodo", ConfiguracionControl.devuelvePeriodoActual());
			query.setParameter("est", Constantes.NO_PAGO);
			total = (Long) query.uniqueResult();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			sc.closeSession();
		}
		return total;
	}

	public void actualizaGastosComunesAConvenios(Unidad unidad) {
		Query query = sc.useSession().createQuery("UPDATE Gastoscomunes SET estado = :nuevoEstado "
			+ "WHERE estado = :elEstado "
			+ "AND unidad=:laUnidad "
			+ "AND periodo<:elPeriodo");
		query.setInteger("nuevoEstado", Constantes.CONVENIO);
		query.setInteger("elEstado", Constantes.NO_PAGO);
		query.setInteger("elPeriodo", ConfiguracionControl.devuelvePeriodoActual());
		query.setParameter("laUnidad", unidad);
		query.executeUpdate();
		tx.commit();
		sc.closeSession();
	}
}
