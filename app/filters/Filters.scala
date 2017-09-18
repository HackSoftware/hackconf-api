package filters

import javax.inject.Inject

import play.api.http.{DefaultHttpFilters, EnabledFilters}
import play.api.mvc.EssentialFilter

class Filters @Inject()(
  defaultFilters: EnabledFilters,
  canMakeChangesFilter: CanMakeChangesFilter
) extends DefaultHttpFilters(defaultFilters.filters :+ (canMakeChangesFilter: EssentialFilter): _*)
