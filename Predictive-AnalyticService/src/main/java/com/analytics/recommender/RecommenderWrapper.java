package com.analytics.recommender;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.Resources;

/**
 This wrapper simplifies that process. Simply extend this class and
 * implement {@link #buildRecommender()}.
 * 
 * @author kuntal
 */
public abstract class RecommenderWrapper implements Recommender {

  private static final Logger log = LoggerFactory.getLogger(RecommenderWrapper.class);

  private final Recommender delegate;

  protected RecommenderWrapper() throws TasteException, IOException {
    this.delegate = buildRecommender();
  }

  /**
   * @return the {@link Recommender} which should be used to produce recommendations
   *  by this wrapper implementation
   */
  protected abstract Recommender buildRecommender() throws IOException, TasteException;

  @Override
  public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
    return delegate.recommend(userID, howMany);
  }

  @Override
  public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
    return delegate.recommend(userID, howMany, rescorer);
  }

  @Override
  public float estimatePreference(long userID, long itemID) throws TasteException {
    return delegate.estimatePreference(userID, itemID);
  }

  @Override
  public void setPreference(long userID, long itemID, float value) throws TasteException {
    delegate.setPreference(userID, itemID, value);
  }

  @Override
  public void removePreference(long userID, long itemID) throws TasteException {
    delegate.removePreference(userID, itemID);
  }

  @Override
  public DataModel getDataModel() {
    return delegate.getDataModel();
  }

  @Override
  public void refresh(Collection<Refreshable> alreadyRefreshed) {
    delegate.refresh(alreadyRefreshed);
  }

  /**
   * Reads the given resource into a temporary file. This is intended to be used
   * to read data files which are stored as a resource available on the classpath,
   * such as in a JAR file. However for convenience the resource name will also
   * be interpreted as a relative path to a local file, if no such resource is
   * found. This facilitates testing.
   *
   * @param resourceName name of resource in classpath, or relative path to file
   * @return temporary {@link File} with resource data
   * @throws IOException if an error occurs while reading or writing data
   */
  public static File readResourceToTempFile(String resourceName) throws IOException {
    String absoluteResource = resourceName.startsWith("/") ? resourceName : '/' + resourceName;
    log.info("Loading resource {}", absoluteResource);
    InputSupplier<? extends InputStream> inSupplier;
    try {
      URL resourceURL = Resources.getResource(RecommenderWrapper.class, absoluteResource);
      inSupplier = Resources.newInputStreamSupplier(resourceURL);
    } catch (IllegalArgumentException iae) {
      File resourceFile = new File(resourceName);
      log.info("Falling back to load file {}", resourceFile.getAbsolutePath());
      inSupplier = Files.newInputStreamSupplier(resourceFile);
    }
    File tempFile = File.createTempFile("taste", null);
    tempFile.deleteOnExit();
    Files.copy(inSupplier, tempFile);
    return tempFile;
  }

}
